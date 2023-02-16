package com.autocrypt.can.ids.service;

import com.autocrypt.can.common.CanIdsDetectionLogParser;
import com.autocrypt.can.common.CanIdsMetaDataCreate;
import com.autocrypt.can.common.CanIdsStatusInfoLogParser;
import com.autocrypt.can.ids.controller.dto.CanIdsLogDto;
import com.autocrypt.can.ids.repository.CanIdsMetaDataRepository;
import com.autocrypt.can.ids.repository.CanIdsDetectionLogRepository;
import com.autocrypt.can.ids.repository.CanIdsStatusInfoLogRepository;

import com.autocrypt.can.ids.repository.entity.CanIdsDetectionLog;
import com.autocrypt.can.ids.repository.entity.CanIdsMetaData;
import com.autocrypt.can.ids.repository.entity.CanIdsStatusInfoLog;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CanIdsLogService {
    private final CanIdsMetaDataRepository canIdsMetaDataRepository;
    private final CanIdsDetectionLogRepository canIdsDetectionLogRepository;
    private final CanIdsStatusInfoLogRepository canIdsStatusInfoLogRepository;


    private void MetaDataSave(CanIdsLogDto canIdsLogDto) {
        CanIdsMetaData canIdsMetaData = canIdsLogDto.CanIdsMetaDataDtoToCanIdsMetaDataEntity(canIdsLogDto.getMetaDataDto());
        canIdsMetaDataRepository.saveAndFlush(canIdsMetaData);
    }

    private String DetectionLogSaveAll(CanIdsLogDto canIdsLogDto) {
        List<CanIdsDetectionLog> canIdsDetectionLogs = canIdsLogDto.CanIdsDetectionDtoToCanIdsDetectionEntity(canIdsLogDto.getDetectionLogsDto());
        canIdsDetectionLogRepository.saveAllAndFlush(canIdsDetectionLogs);

        String seriousness = "NORMAL";
        Map<String, Long> seriousnessMap = canIdsDetectionLogs.stream()
                .collect(Collectors.groupingBy(CanIdsDetectionLog::getSeverity, Collectors.counting()));
        if (Optional.ofNullable(seriousnessMap.get("high")).orElse(0L) != 0L) {
            seriousness = "DANGER";
        } else if (Optional.ofNullable(seriousnessMap.get("middle")).orElse(0L) != 0L) {
            seriousness = "WARNING";
        }
        return seriousness;
    }

    private void StatusInfoLogSave(CanIdsLogDto canIdsLogDto) {
        CanIdsStatusInfoLog canIdsStatusInfoLog = canIdsLogDto.CanIdsStatusInfoDtoToCanIdsStatusInfoEntity(canIdsLogDto.getStatusInfoLogDto());
        canIdsStatusInfoLogRepository.saveAndFlush(canIdsStatusInfoLog);
    }

    // Check whether the received CAN IDS log is normal
    public ResponseEntity<?> receiveCanIdsLog(CanIdsLogDto.RequestDataDto data) throws ParseException {
        String result;

        if (data.getBinary().isEmpty()) {
            result = "Data Empty!";
        } else {
            result = MappingCanIdsLog(data);
        }

        if (result.equals("NORMAL") || result.equals("WARNING") || result.equals("DANGER")) {
            CanIdsLogDto.ResponseDataDto responseDataDto =
                    CanIdsLogDto.ResponseDataDto.builder()
                            .seriousness(result)
                            .build();
            return ResponseEntity.status(HttpStatus.OK).body(responseDataDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Parse the received CAN IDS log
    @Transactional
    String MappingCanIdsLog(CanIdsLogDto.RequestDataDto data) throws ParseException {
        String result = "";
        byte[] binaryData = Hex.decode(data.getBinary());

        int detectionLogCnt = binaryData.length / CanIdsDetectionLogParser.DETECTION_LOG_SIZE;
        int statusInfoLogCnt = binaryData.length % CanIdsDetectionLogParser.DETECTION_LOG_SIZE;

        // Since Status Info Count is always 1, it is treated as 0 if it is not 48 bytes!
        statusInfoLogCnt = (statusInfoLogCnt == CanIdsStatusInfoLogParser.STATUSINFO_LOG_SIZE ? 1 : 0);

        CanIdsLogDto canIdsLogDto = new CanIdsLogDto();

        // MetaDataLog Create
        canIdsLogDto.setMetaDataDto(CanIdsMetaDataCreate.setMetaData(data.getVehicleId(), data.getRegion(), data.getCity()));

        if (0 != detectionLogCnt && 0 != statusInfoLogCnt) {
            // DetectionLog Insert
            for (int i = 0; i < detectionLogCnt; i++) {
                byte[] bin = Arrays.copyOfRange(binaryData,
                        (CanIdsDetectionLogParser.DETECTION_LOG_SIZE * i),
                        (CanIdsDetectionLogParser.DETECTION_LOG_SIZE * i) + CanIdsDetectionLogParser.DETECTION_LOG_SIZE);

                canIdsLogDto.getDetectionLogsDto().add(CanIdsDetectionLogParser.setDetectionLog(bin,
                                                        canIdsLogDto.getMetaDataDto().getId()));
            }

            // StatusLog Insert
            byte[] bin = Arrays.copyOfRange(binaryData,
                                    CanIdsDetectionLogParser.DETECTION_LOG_SIZE * detectionLogCnt,
                                      (CanIdsDetectionLogParser.DETECTION_LOG_SIZE * detectionLogCnt) + 48);

            canIdsLogDto.setStatusInfoLogDto(CanIdsStatusInfoLogParser.setStatusInfoLog(bin,
                                            canIdsLogDto.getMetaDataDto().getId()));

            // Data Save
            MetaDataSave(canIdsLogDto);
            result = DetectionLogSaveAll(canIdsLogDto);
            StatusInfoLogSave(canIdsLogDto);

        } else if (0 != statusInfoLogCnt) {
            // StatusLog Insert
            canIdsLogDto.setStatusInfoLogDto(CanIdsStatusInfoLogParser.setStatusInfoLog(binaryData,
                                            canIdsLogDto.getMetaDataDto().getId()));

            // Data Save
            MetaDataSave(canIdsLogDto);
            StatusInfoLogSave(canIdsLogDto);

            result = "NORMAL";
        } else {
            result = "Data Error!";
        }

        return result;
    }

}
