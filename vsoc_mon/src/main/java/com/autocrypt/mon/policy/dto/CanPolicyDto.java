package com.autocrypt.mon.policy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CanPolicyDto {

    @JsonProperty("Header")
    private Header header;

    @JsonProperty("Body")
    private Body body;


    @Setter @Getter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor @AllArgsConstructor
    public static class Header {

        @JsonProperty("busCount")
        private int busCount;

        @JsonProperty("busInfo")
        private HashMap<String,BusInfo> busInfo;
    }

    @Setter @Getter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor @AllArgsConstructor
    public static class BusInfo {

        @JsonProperty("busName")
        private String busName;

        @JsonProperty("busNumber")
        private int busNumber;
    }


    @Setter @Getter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor @AllArgsConstructor
    public static class Body {
        @JsonProperty("messageInfo")
        private HashMap<String,MessageInfo> messageInfo;

        @JsonProperty("signalInfo")
        private HashMap<String,SignalInfo> signalInfo;
    }

    @Setter @Getter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor @AllArgsConstructor
    public static class MessageInfo {
        @JsonProperty("busName")
        private String busName;

        @JsonProperty("messageId")
        private int messageId;

        @JsonProperty("messageRxEcu")
        private List<String> messageRxEcu;

        @JsonProperty("messageTxEcu")
        private List<String> messageTxEcu;
    }

    @Setter @Getter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor @AllArgsConstructor
    public static class SignalInfo {

        @JsonProperty("busName")
        private String busName;

        @JsonProperty("messageId")
        private int messageId;

        @JsonProperty("signalName")
        private String signalName;

        @JsonProperty("signalStartBit")
        private int signalStartBit;
    }
}
