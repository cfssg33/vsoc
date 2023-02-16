package com.autocrypt.host.idps;


import com.autocrypt.host.idps.controller.dto.HostIdpsRuleDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class HostIdpsRuleMapping {

    private final String HOST_IDPS_RULE_FILE_PATH = "json/rule/HostIdpsRule.json";
    public static HostIdpsRuleDto hostIdpsRuleDto = new HostIdpsRuleDto();

    @PostConstruct
    void init () {
        try {
            //Read the HostIdpsRule.json file to set the rule in memory.
            ClassPathResource classPathResource = new ClassPathResource(this.HOST_IDPS_RULE_FILE_PATH);
            if(classPathResource.exists() == false){
                log.debug("Invalid filePath : {}",  this.HOST_IDPS_RULE_FILE_PATH);
                throw new IllegalArgumentException();
            }

            ObjectMapper mapper = new ObjectMapper();
            hostIdpsRuleDto = mapper.readValue(classPathResource.getInputStream(), HostIdpsRuleDto.class);
        } catch (Exception e) {
            log.debug("Failed to read json file : " + e);
        }
    }
}
