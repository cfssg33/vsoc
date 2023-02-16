package com.autocrypt.host.idps.controller.dto;

import com.autocrypt.host.common.HostIdpsUtil;
import com.autocrypt.host.idps.model.HostIdpsLogMapper;
import com.autocrypt.host.idps.repository.entity.HostIdpsMetadata;
import com.autocrypt.host.idps.repository.entity.HostIdpsStatusLog;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
public class HostIdpsLogDTO {

    @Setter
    @Getter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HostIdpsResponse {
        private String seriousness;
    }

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HostIdpsReq {

        private int eid;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime ts;

        private String dt;
        private String vn;
        private String dtn;
        private String vehicleId;
        private String region;
        private String city;
        private String policy;

        @JsonProperty("status_log")
        private statusLog statusLogs;

        @JsonProperty("detection_log")
        private List<DetectionLog> detectionLogs;

        @Setter
        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @NoArgsConstructor
        @AllArgsConstructor
        public static class statusLog {

            private String uid;

            @JsonProperty("event_time")
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            private LocalDateTime eventTime;

            @JsonProperty("ids_policy_ver")
            private String idsPolicyVer;

            @JsonProperty("fw_policy_ver")
            private String fwPolicyVer;

            @JsonProperty("traffic_rx")
            private int trafficRx;

            @JsonProperty("traffic_tx")
            private int trafficTx;
        }


        @Setter
        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DetectionLog {

            private String uid;

            @JsonProperty("event_time")
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            private LocalDateTime eventTime;

            private String protocol;

            @JsonProperty("rule_id")
            private int ruleId;

            @JsonProperty("src_ip")
            private String srcIp;

            @JsonProperty("src_port")
            private int srcPort;

            @JsonProperty("dst_ip")
            private String dstIp;

            @JsonProperty("dst_port")
            private int dstPort;

            @JsonProperty("type_id")
            private int typeId;
        }

        public static HostIdpsMetadata toHostIdpsMetadataEntity(HostIdpsLogDTO.HostIdpsReq hostIdpsLogDTO) {
            HostIdpsMetadata hostIdpsMetadata = HostIdpsLogMapper.INSTANCE.toHostIdpsMetadataEntity(hostIdpsLogDTO);
            hostIdpsMetadata.setHostLog(HostIdpsUtil.uuid());

            return hostIdpsMetadata;
        }

        public static HostIdpsStatusLog toHostIdpsStatusLogEntity(HostIdpsLogDTO.HostIdpsReq.statusLog statusLog, HostIdpsMetadata metadata) {
            HostIdpsStatusLog hostIdpsStatusLog = HostIdpsLogMapper.INSTANCE.toHostIdpsStatusLogEntity(statusLog);
            hostIdpsStatusLog.setUid(HostIdpsUtil.uuid());
            hostIdpsStatusLog.setHostIdpsMetadata(metadata);

            return hostIdpsStatusLog;
        }
    }
}
