package com.autocrypt.mon.vehicleInfo.dto;

import lombok.*;

import java.util.Date;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VehicleDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class VehicleItem {
        private String vehicleId;
        private String policy;
        private String version;
        private Date registeredDate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class VehicleDetails {
        private String vehicleId;
        private String brand;
        private String type;
        private String size;
        private String registeredDate;
        private String registeredCountry;
        private String lastDate;
        private String lastCountry;
        private String policyName;
        private String version;
    }

    @Getter
    @AllArgsConstructor
    public static class VehicleGroupItem {
        private final String id;
        private final String no;
        private final String vehicleId;
        private final String brand;
        private final String type;
        private final int size;
        private final String appliedDate;
        private final String appliedPolicy;
        private final String publishedDate;
        private final String publishedPolicy;
    }

    @Getter
    @AllArgsConstructor
    public static class VehicleGroupItems {
        private final List<VehicleGroupItem> vehicleGroupItems;
    }

    @Getter
    @AllArgsConstructor
    public static class VehicleGroupList {
        private final VehicleGroupItems vehicleGroupLists[];
    }

    @Getter
    @AllArgsConstructor
    public static class PlatformItem {
        private final String id;
        private final String name;
        private final String registeredDate;
        private final int modelNum;
        private final int groupNum;
        private final int vehicleNum;
        private final String policyPublishedStatus;
    }

    @Getter
    @AllArgsConstructor
    public static class PlatformList {
        List<PlatformItem> platformItems;
    }

    @Getter
    @AllArgsConstructor
    public static class PlatformDbData {
        private final String no;
        private final String busNumber;
        private final String canDbName;
        private final Date generated;
        private final Date lastUpdated;
    }

    @Getter
    @AllArgsConstructor
    public static class PlatformDbDataList {
        private final List<PlatformDbData> platformDbDatas;
    }

    @Getter
    @AllArgsConstructor
    public static class PlatformStat {
        private final int vehicleNum;
        private final int modelsNum;
        private final int groupsNum;
    }

}
