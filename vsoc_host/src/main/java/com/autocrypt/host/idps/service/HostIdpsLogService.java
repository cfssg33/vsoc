package com.autocrypt.host.idps.service;

import com.autocrypt.host.common.HostIdpsUtil;
import com.autocrypt.host.exception.HostIdpsException;
import com.autocrypt.host.idps.HostIdpsRuleMapping;
import com.autocrypt.host.idps.controller.dto.HostIdpsLogDTO;
import com.autocrypt.host.idps.repository.HostIdpsLogRepository;
import com.autocrypt.host.idps.repository.HostIdpsMetaDataRepository;
import com.autocrypt.host.idps.repository.HostIdpsStatusRepository;
import com.autocrypt.host.idps.repository.entity.HostIdpsDetectionLog;
import com.autocrypt.host.idps.repository.entity.HostIdpsMetadata;
import com.autocrypt.host.idps.repository.entity.HostIdpsStatusLog;
import lombok.RequiredArgsConstructor;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HostIdpsLogService {

    private final HostIdpsMetaDataRepository hostIdpsMetaDataRepository;
    private final HostIdpsLogRepository hostIdpsLogRepository;
    private final HostIdpsStatusRepository hostIdpsStatusRepository;

    @Transactional
    public ResponseEntity<?> reportVehicleLog (HostIdpsLogDTO.HostIdpsReq hostIdpsReq) throws HostIdpsException {

        hostIdpsReq.setPolicy("");

        //The request parameter of host idps is changed to host idps metadata format.
        HostIdpsMetadata hostIdpsMetadata = Optional.ofNullable(HostIdpsLogDTO.HostIdpsReq.toHostIdpsMetadataEntity(hostIdpsReq))
                        .orElseThrow(()-> new HostIdpsException("HostIdpsMetadata conversion failed in HostIdpsLogDTO"));
        hostIdpsMetaDataRepository.saveAndFlush(hostIdpsMetadata);

        //Parse the host idps detection log and insert it into db.
        List<HostIdpsDetectionLog> hostIdpsDetectionLogList = Optional.ofNullable(this.setHostIdpsDetectionLogObj(hostIdpsMetadata))
                        .orElseThrow(()-> new HostIdpsException("set failed in HostIdpsDetectionLog"));
        hostIdpsLogRepository.saveAllAndFlush(hostIdpsDetectionLogList);

        // Parse the host idps status log and insert it into db
        HostIdpsStatusLog hostIdpsStatusLog = Optional.ofNullable(HostIdpsLogDTO.HostIdpsReq.toHostIdpsStatusLogEntity(hostIdpsReq.getStatusLogs(), hostIdpsMetadata))
                .orElseThrow(()-> new HostIdpsException("set failed in HostIdpsStatusLog"));
        hostIdpsStatusRepository.saveAndFlush(hostIdpsStatusLog);

        HostIdpsLogDTO.HostIdpsResponse hostIdpsResponse = HostIdpsLogDTO.HostIdpsResponse.builder()
                .seriousness(this.getSeriousness(hostIdpsDetectionLogList))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(hostIdpsResponse);
    }

    private List<HostIdpsDetectionLog> setHostIdpsDetectionLogObj(HostIdpsMetadata hostIdpsMetadata) {
        List<HostIdpsDetectionLog> hostIdpsDetectionLogs = hostIdpsMetadata.getHostIdpsDetectionLogs();

        hostIdpsDetectionLogs.forEach(log -> {
            log.setUid(HostIdpsUtil.uuid());

            if (log.getHostIdpsMetadata() == null) {
                log.setHostIdpsMetadata(new HostIdpsMetadata());
            }

            log.getHostIdpsMetadata().setHostLog(hostIdpsMetadata.getHostLog());

            String hostIdpsRule = this.findHostIdpsRule(log.getRuleId());
            String[] rules = new String[2];
            if (StringUtils.isEmpty(hostIdpsRule) == false) {
                rules = hostIdpsRule.split("\\|");
            }

            log.setRuleName(rules[0].length() > 0 ? rules[0] : Integer.toString(log.getRuleId()));
            log.setRuleType(log.getRuleId() < 1000001 ? "Firewall" : "IDPS");
            log.setSeverity(rules[1].length() > 0 ? rules[1] : "");
        });

        return hostIdpsDetectionLogs;
    }

    private String findHostIdpsRule (int ruleId) {

        String value= "";
        if (ruleId < 1000001) {
            value = HostIdpsRuleMapping.hostIdpsRuleDto.getFirewallRules().get(Integer.toString(ruleId));
        } else {
            value = HostIdpsRuleMapping.hostIdpsRuleDto.getIdsRules().get(Integer.toString(ruleId));
        }
        return value;
    }

    private String getSeriousness(List<HostIdpsDetectionLog> hostIdpsDetectionLogList) {
        String seriousness = "NORMAL";
        Map<String, Long> seriousnessMap = hostIdpsDetectionLogList.stream()
                .collect(Collectors.groupingBy(HostIdpsDetectionLog::getSeverity, Collectors.counting()));
        if (Optional.ofNullable(seriousnessMap.get("high")).orElse(0L) != 0L) {
            seriousness = "DANGER";
        } else if (Optional.ofNullable(seriousnessMap.get("middle")).orElse(0L) != 0L) {
            seriousness = "WARNING";
        }
        return seriousness;
    }
}
