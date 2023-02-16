package com.autocrypt.host.idps.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HostIdpsRuleDto {

    @JsonProperty("firewall_rule")
    private HashMap<String, String> firewallRules;

    @JsonProperty("idps_rule")
    private HashMap<String, String> idsRules;
}
