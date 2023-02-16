package com.autocrypt.can.ids.controller.dto;

import com.autocrypt.can.ids.model.CanIdsLogMapper;
import com.autocrypt.can.ids.repository.entity.CanIdsDetectionLog;
import com.autocrypt.can.ids.repository.entity.CanIdsMetaData;
import com.autocrypt.can.ids.repository.entity.CanIdsStatusInfoLog;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CanIdsLogDto {

    private MetaDataDto metaDataDto;
    private List<DetectionLogDto> detectionLogsDto = new ArrayList<>();
    private StatusInfoLogDto statusInfoLogDto;


    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RequestDataDto {
        @JsonProperty("vehicle_info_pk")
        private String vehicleId;

        @JsonProperty("region")
        private String region;

        @JsonProperty("city")
        private String city;

        @JsonProperty("binary")
        private String binary;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseDataDto {
        private String seriousness;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class MetaDataDto {
        private String id;
        private Date createTime;
        private String vehicleId;
        private String region;
        private String city;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class DetectionLogDto {
        private int id;
        private String matchId;
        private int logType;                // 1bit
        private int dataLength;             // 7bit
        private int byCanBusNum;            // 1byte
        private int byViolationRuleId;      // 1byte
        private String byViolationRuleName;
        private int signalStartBit;         // 2byte
        private int signalLength;           // 2byte
        private int byRawMessageLength;     // 1byte
        private int canId;                  // 4byte
        private Date detectionTime;
        private int detectionReasonHi;      // 4byte
        private int detectionReasonLo;      // 4byte
        private int duplicationNumber;      // 2byte
        private String reserved;            // 14byte
        private String byRawMessageBody;    // 64byte
        private String label;
        private String checkedLog;
        private String severity;
    };


    @Getter
    @Setter
    @NoArgsConstructor
    public static class StatusInfoLogDto {
        private String id;
        private int logType;                // 1bit
        private int dataLength;             // 7bit
        private int sequenceNumber;         // 2byte
        private int memoryUsage;            // 4byte
        private int flashUsage;             // 4byte
        private String byPolicyVersion;     // 6byte
        private String byFirmwareVersion;   // 6byte
        private String reserved;            // 25byte
        private String checkedLog;
    };


    public static CanIdsMetaData CanIdsMetaDataDtoToCanIdsMetaDataEntity(CanIdsLogDto.MetaDataDto metaDataDto) {
        CanIdsMetaData canIdsMetaData = CanIdsLogMapper.INSTANCE.CanIdsMetaDataDtoToCanIdsMetaDataEntity(metaDataDto);
        return canIdsMetaData;
    }

    public static List<CanIdsDetectionLog> CanIdsDetectionDtoToCanIdsDetectionEntity(List<CanIdsLogDto.DetectionLogDto> detectionLogDto) {
        List<CanIdsDetectionLog> canIdsDetectionLogList = CanIdsLogMapper.INSTANCE.CanIdsDetectionDtoToCanIdsDetectionEntity(detectionLogDto);
        return canIdsDetectionLogList;
    }

    public static CanIdsStatusInfoLog CanIdsStatusInfoDtoToCanIdsStatusInfoEntity(CanIdsLogDto.StatusInfoLogDto statusInfoLogDto) {
        CanIdsStatusInfoLog canIdsStatusInfoLog = CanIdsLogMapper.INSTANCE.CanIdsStatusInfoDtoToCanIdsStatusInfoEntity(statusInfoLogDto);
        return canIdsStatusInfoLog;
    }

}
