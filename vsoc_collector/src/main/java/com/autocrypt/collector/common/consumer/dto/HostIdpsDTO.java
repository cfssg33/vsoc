package com.autocrypt.collector.common.consumer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class HostIdpsDTO {

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
}