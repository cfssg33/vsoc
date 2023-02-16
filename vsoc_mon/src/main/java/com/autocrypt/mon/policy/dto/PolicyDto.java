package com.autocrypt.mon.policy.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PolicyDto {

    // 현재는 CanIds와 HostIdps가 항목들이 같아도 추후 각각 따로 추가되는 항목들에 대비하여 DTO 나누어둠.
    @Getter
    @AllArgsConstructor
    public static class CanIdsPolicy {
        private final int id;
        private final int no;
        private final String policyName;
        private final String latestVersion;
        private final String status;
        private final Date updated;
        private final String updatedBy;
    }

    @Getter
    @AllArgsConstructor
    public static class CanIdsPolicyList {
        private final List<CanIdsPolicy> canIdsPolicies;
    }

    @Getter
    @AllArgsConstructor
    public static class HostIdpsPolicy {
        private final int id;
        private final int no;
        private final String policyName;
        private final String latestVersion;
        private final String status;
        private final Date updated;
        private final String updatedBy;
    }

    @Getter
    @AllArgsConstructor
    public static class HostIdpsPolicyList {
        private final List<HostIdpsPolicy> hostIdpsPolicies;
    }

    @Getter
    @AllArgsConstructor
    public static class PolicyVersionItem {
        private final String version;
        private final String status;
        private final Date modified;
        private final String modifiedBy;
        private final Date published;
        private final String publishedBy;
    }

    @Getter
    @AllArgsConstructor
    public static class PolicyVersion {
        private final List<PolicyVersionItem> policyVersionItems;
    }

    @Getter
    @AllArgsConstructor
    public static class PolicyHistoryItem {
        private final int id;
        private final int no;
        private final int vehicles;
        private final Date publishedTime;
        private final String publishedBy;
        private final String installationRate;
    }

    @Getter
    @AllArgsConstructor
    public static class PolicyHistory {
        private final List<PolicyHistoryItem> policyHistoryItems;
    }

    @Getter
    @AllArgsConstructor
    public static class PolicyDetail {
        private final String name;
        private final PolicyVersion policyVersion;
        private final PolicyHistory policyHistory;
    }

    @Getter
    @AllArgsConstructor
    public static class PolicyLogItem {
        private final int id;
        private final String vehicleId;
        private final String policy;
        private final String version;
        private final Date installationDate;
        private final boolean installationStatus;
    }

    @Getter
    @AllArgsConstructor
    public static class PolicyLogList {
        private final List<PolicyLogItem> policyLogs;
    }

    @Getter
    @AllArgsConstructor
    public static class PolicyDbItem {
        private final String no;
        private final String busNumber;
        private final String canDbName;
        private final Date generated;
        private final Date lastUpdated;
    }

    @Getter
    @AllArgsConstructor
    public static class PolicyDbList {
        private final List<PolicyDbItem> policyDbs;
    }

    @Getter
    @AllArgsConstructor
    public static class PolicyBusItem {
        private final String no;
        private final String busNumber;
        private final String ruleType;
        private final String expectedValue;
    }

    @Getter
    @AllArgsConstructor
    public static class PolicyBusList {
        private final List<PolicyBusItem> policyBusItems;
    }

    @Getter
    @AllArgsConstructor
    public static class PolicyBusData {
        private final String[] busNumbers;
        private final String[] ruleTypes;
        private final PolicyBusList policyBusList;
    }

    @Getter @Setter
    @Builder
    @AllArgsConstructor
    public static class PolicyInfo {
        private final MultipartFile policyFile;
        private final String policyName;
        private final String policyVersion;
        private final String userName;
    }

    @Getter @Setter
    @Builder
    @AllArgsConstructor
    public static class PolicyListInfo {
        private String policyName;
        private String policyVersion;
        private String status;
        private String updatedBy;
        private LocalDateTime updateTime;

    }

}
