package com.autocrypt.mon.hostidps.service;

import com.autocrypt.mon.common.ExcelService;
import com.autocrypt.mon.hostidps.dto.HostIdpsDto;
import com.autocrypt.mon.hostidps.repository.HostIdpsDetectionLogRepository;
import com.autocrypt.mon.hostidps.repository.HostIdpsTopFiveRule;
import com.autocrypt.mon.hostidps.repository.entity.HostIdpsDetectionLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HostIdpsService {

    private final HostIdpsDetectionLogRepository hostIdpsDetectionLogRepository;
    private final ExcelService excelService;

    public HostIdpsDto.HostIdpsOverall GetHostIdpsOverall(String region, String city, String startDate, String endDate)
            throws ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDateTime = transFormat.parse(startDate);
        Date endDateTime = transFormat.parse(endDate);
        Long calculateDate = (endDateTime.getTime() - startDateTime.getTime()) / (60*1000);

        int hostIdpsTotalDetections = hostIdpsDetectionLogRepository.totalDetectionCount(startDateTime, endDateTime);
        double hostIdpsAverage = hostIdpsDetectionLogRepository.detectionAverage(calculateDate, startDateTime, endDateTime);
        int hostIdpsDetectionVehicleIdCount = hostIdpsDetectionLogRepository.countDetectionVehicleId(startDateTime, endDateTime);

        List<HostIdpsTopFiveRule> hostIdpsTopFiveRules = hostIdpsDetectionLogRepository.countTopFiveRules(startDateTime, endDateTime);

        List<HostIdpsDto.HostIdpsTopFiveRules> hostIdpsTopFiveRuleList = new ArrayList<>();
        hostIdpsTopFiveRules.stream().forEach(i -> {
            hostIdpsTopFiveRuleList.add(new HostIdpsDto.HostIdpsTopFiveRules(i.getName(), Long.valueOf(i.getCnt())));
        });

        return new HostIdpsDto.HostIdpsOverall(hostIdpsTotalDetections,
               hostIdpsAverage,
               hostIdpsDetectionVehicleIdCount,
               hostIdpsTopFiveRuleList);
    }

    public HostIdpsDto.HostIdpsLogListResponse GetHostIdpsLogList(String type, String vehicleId, String startDate, String endDate, int itemsPerPage, int pageNum)
            throws ParseException {

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDateTime = transFormat.parse(startDate);
        Date endDateTime = transFormat.parse(endDate);

        if (StringUtils.hasText(vehicleId) == true) {
            return new HostIdpsDto.HostIdpsLogListResponse();
        } else {
            List<HostIdpsDetectionLog> hostIdpsDetectionLogList = new ArrayList<>();
            long totalCount;
            if (type.equals("All")) {
                hostIdpsDetectionLogList = hostIdpsDetectionLogRepository
                        .findDetectionLogOrderByEventTimeWithLimit(startDateTime, endDateTime, PageRequest.of(pageNum-1, itemsPerPage));
                totalCount = hostIdpsDetectionLogRepository.findDetectionLogCount(startDateTime, endDateTime);
            } else {
                hostIdpsDetectionLogList = hostIdpsDetectionLogRepository
                        .findDetectionLogByRuleTypeOrderByEventTimeWithLimit(type, startDateTime, endDateTime, PageRequest.of(pageNum-1, itemsPerPage));
                totalCount = hostIdpsDetectionLogRepository.findDetectionLogCountByRuleType(type, startDateTime, endDateTime);
            }

            List<HostIdpsDto.HostIdpsLog> hostIdpsLogList = hostIdpsDetectionLogList.stream()
                    .map(HostIdpsDetectionLog::toHostIdpsLog)
                    .collect(Collectors.toList());

            long totalPage = ((long)Math.ceil(totalCount / itemsPerPage)) + (((long)Math.ceil(totalCount % itemsPerPage)) != 0 ? 1 : 0);


            return HostIdpsDto.HostIdpsLogListResponse.builder()
                    .hostIdpsLogList(hostIdpsLogList)
                    .totalCount(totalCount)
                    .totalPage(totalPage)
                    .build();
        }
    }

    public void ChangeHostIdpsLogLabel(HostIdpsDto.LabelChangeReq req) {
        Optional<HostIdpsDetectionLog> hostIdpsDetectionLog = hostIdpsDetectionLogRepository.findById(req.getUid());

        hostIdpsDetectionLog.ifPresent(log -> {
            log.setLabel(req.getLabel());
            hostIdpsDetectionLogRepository.saveAndFlush(log);
        });
    }

    public byte[] exportHostIdpsLog() throws Exception {
        List<HostIdpsDetectionLog> hostIdpsDetectionLogList = hostIdpsDetectionLogRepository.findAll();
        List<HostIdpsDto.HostIdpsLog> hostIdpsLogs = hostIdpsDetectionLogList.stream()
                .map(HostIdpsDetectionLog::toHostIdpsLog)
                .collect(Collectors.toList());

        ArrayList<Map> hostIdpsLogMapList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (HostIdpsDto.HostIdpsLog hostIdpsLog : hostIdpsLogs) {
            hostIdpsLogMapList.add(objectMapper.convertValue(hostIdpsLog, Map.class));
        }
        return excelService.makeExcel("hostIdpsLog", hostIdpsLogMapList);
    }

}
