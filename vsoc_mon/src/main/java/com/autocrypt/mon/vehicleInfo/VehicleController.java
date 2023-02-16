package com.autocrypt.mon.vehicleInfo;

import com.autocrypt.mon.vehicleInfo.dto.VehicleDto;
import com.autocrypt.mon.vehicleInfo.service.VehicleService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    //Vehicle list response content.
    @ResponseBody
    @GetMapping("/vehicle")
    public List<VehicleDto.VehicleItem> getVehicleList() {
        return vehicleService.getVehicleList();
    }

    //The detailed information of the vehicle is responded.
    @ResponseBody
    @GetMapping("/vehicle/{vehicleId}")
    public VehicleDto.VehicleDetails getVehicleDetail(@PathVariable("vehicleId") String vehicleId) throws Exception {
        return vehicleService.getVehicleDetail(vehicleId);
    }

    //Response to the vehicle group list.
    @ResponseBody
    @GetMapping("/vehicle/group")
    public VehicleDto.VehicleGroupList getVehicleGroupList() {
        return vehicleService.getVehicleGroupList();
    }

    @ResponseBody
    @GetMapping("/platform")
    public VehicleDto.PlatformList getPlatformList(@RequestBody Optional<String> name) {
        return vehicleService.getPlatformList(name);
    }

    @ResponseBody
    @GetMapping("/platform/{platformId}/db")
    public VehicleDto.PlatformDbDataList getPlatformDbData(@PathVariable("platformId") String platformId) {
        return vehicleService.getPlatformDbData(platformId);
    }

    @ResponseBody
    @GetMapping("/platform/{platformId}/stat")
    public VehicleDto.PlatformStat getPlatformStat(@PathVariable("platformId") String platformId) {
        return vehicleService.getPlatformStat(platformId);
    }

}
