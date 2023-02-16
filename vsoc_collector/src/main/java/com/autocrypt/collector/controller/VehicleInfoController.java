package com.autocrypt.collector.controller;

import com.autocrypt.collector.controller.dto.VehicleInfoDTO;
import com.autocrypt.collector.repository.entity.VehicleInfo;
import com.autocrypt.collector.service.VehicleInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/carinfo")
@RequiredArgsConstructor
public class VehicleInfoController {

    private final VehicleInfoService vehicleInfoService;

    //@GetMapping("/test")
    //public VehicleInfo getCarInfoByVin(@RequestBody VehicleInfoDTO.GetCarInfoByBinReq req) throws Exception {
    //    VehicleInfoDTO.AccessTokenResp accessTokenResp = vehicleInfoService.getAccessToken();
    //    return vehicleInfoService.getVehicleInfo(accessTokenResp.getAccess_token(), req.getVin());
    //}
}
