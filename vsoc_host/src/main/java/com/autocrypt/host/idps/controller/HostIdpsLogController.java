package com.autocrypt.host.idps.controller;

import com.autocrypt.host.exception.HostIdpsException;
import com.autocrypt.host.idps.controller.dto.HostIdpsLogDTO;
import com.autocrypt.host.idps.service.HostIdpsLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/host/idps")
@RequiredArgsConstructor
public class HostIdpsLogController {

    private final HostIdpsLogService hOstIdpsLogService;

    //Recorded host idps logs in db
    @RequestMapping(
            value = "/log",
            method = RequestMethod.PUT
    )
    public ResponseEntity<?> reportVehicleLog(@RequestBody HostIdpsLogDTO.HostIdpsReq hostIdpsReq) throws HostIdpsException {
        return hOstIdpsLogService.reportVehicleLog(hostIdpsReq);
    }
}
