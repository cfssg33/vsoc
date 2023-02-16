package com.autocrypt.mon.policy;


import com.autocrypt.mon.policy.dto.CanPolicyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class CanPolicy  {

    private final String CAN_POLICY_FILE_PATH = "json/policy/CanPolicy.json";
    public static CanPolicyDto canPolicyDto = new CanPolicyDto();

    @PostConstruct
    void init () {
        try {
            ClassPathResource classPathResource = new ClassPathResource(this.CAN_POLICY_FILE_PATH);
            if(classPathResource.exists() == false){
                log.debug("Invalid filePath : {}",  this.CAN_POLICY_FILE_PATH);
                throw new IllegalArgumentException();
            }

            ObjectMapper mapper = new ObjectMapper();
            canPolicyDto = mapper.readValue(classPathResource.getInputStream(), CanPolicyDto.class);
        }catch (Exception e) {
            log.debug("Failed to read json file : " + e);
        }
    }
}
