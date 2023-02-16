package com.autocrypt.mon.canids.dto;

import lombok.*;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CanIdsDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CanIdsTopFiveRules {
        private final String ruleId;
        private final Long count;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CanIdsOverall {
        private final int totalDetections;
        private final double averageDetections;
        private final int detectedVehicles;
        private final List<CanIdsTopFiveRules> canIdsTopFiveRules;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CanIdsLogListResponse {
        private List<CanIdsLog> canIdsLogList;
        private Long totalCount;
        private Long totalPage;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CanIdsLog {
        private int id;
        private String detectedTime;
        private String vehicleId;
        private String policy;
        private String version;
        private String ruleName;
        private int count;
        private List<String> tx;
        private List<String> rx;
        private String busId;
        private String message;
        private String signalId;
        private String rawMessageData;
        private String reason;
        private String label;
        private boolean hidden;
        private String severity;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LabelChangeReq {
        private int id;
        private String label;
    }

}
