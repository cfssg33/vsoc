package com.autocrypt.collector.common.consumer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class CanIdsDTO {

    @JsonProperty("vehicle_info_pk")
    private String vehicleInfoPk;

    @JsonProperty("region")
    private String region;

    @JsonProperty("city")
    private String city;

    @JsonProperty("binary")
    private String binary;

}
