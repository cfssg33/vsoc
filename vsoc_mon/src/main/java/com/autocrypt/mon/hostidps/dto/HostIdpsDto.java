package com.autocrypt.mon.hostidps.dto;

import lombok.*;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HostIdpsDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class HostIdpsTopFiveRules {
        private final String ruleId;
        private final Long count;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class HostIdpsOverall {
        private final int totalDetections;
        private final double averageDetections;
        private final int detectedVehicles;
        private final List<HostIdpsTopFiveRules> hostIdpsTopFiveRules;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HostIdpsLogListResponse {
        private List<HostIdpsLog> hostIdpsLogList;
        private Long totalCount;
        private Long totalPage;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HostIdpsLog {
        private String uid;
        private String detectedTime;
        private String vehicleId;
        private String logType;
        private String policy;
        private String version;
        private String ruleName;
        private String protocol;
        private String srcIp;
        private String srcPort;
        private String dstIp;
        private String dstPort;
        private String severity;
        private String label;
        private boolean hidden;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LabelChangeReq {
        private String uid;
        private String label;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VulnerableRegionDto {
        private String region;
        private Long warning;
        private Long danger;
    }

}
