package com.autocrypt.mon.monitor.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MonitorPanelDto {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class OverallThreatLevel{
        private final int count;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class TotalVehicles{
        private final long count;
        private final long danger;
        private final long warning;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class VehiclePlatformType{
        private final int count;
        private final int appliedSecurityPolicy;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Alarm{
        private final String name;
        private final String pk;
        private final long createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class DetectedCanLogs{
        private final long count;
        private final List<Alarm> alarms;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class DetectedHostLogs{
        private final long count;
        private final List<Alarm> alarms;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MonitorPanelResp {
        private final OverallThreatLevel overallThreatLevel;
        private final TotalVehicles totalVehicles;
        private final VehiclePlatformType vehiclePlatformType;
        private final DetectedCanLogs detectedCanLogs;
        private final DetectedHostLogs detectedHostLogs;
    }
}
