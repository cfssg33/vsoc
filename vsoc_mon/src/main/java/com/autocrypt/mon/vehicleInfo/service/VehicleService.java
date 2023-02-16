package com.autocrypt.mon.vehicleInfo.service;

import com.autocrypt.mon.vehicleInfo.dto.VehicleDto;
import com.autocrypt.mon.vehicleInfo.repository.VehicleInfoRepository;
import com.autocrypt.mon.vehicleInfo.repository.entity.VehicleInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleInfoRepository vehicleInfoRepository;

    public List<VehicleDto.VehicleItem> getVehicleList() {
        List<VehicleInfo> vehicleInfoList = vehicleInfoRepository.findAll();
        List<VehicleDto.VehicleItem> vehicleIdList = vehicleInfoList.stream()
                .map(VehicleInfo::toVehicleItem)
                .collect(Collectors.toList());

        return vehicleIdList;
    }

    public VehicleDto.VehicleDetails getVehicleDetail(String vehicleId) throws Exception {
        Optional<VehicleInfo> optionalVehicleInfo = vehicleInfoRepository.findByVinEquals(vehicleId);
        VehicleInfo vehicleInfo = optionalVehicleInfo.orElseThrow(Exception::new);

        return VehicleDto.VehicleDetails.builder()
                .vehicleId(vehicleInfo.getVin())
                .brand(vehicleInfo.getBrandName())
                .type(vehicleInfo.getCarName())
                .size("-")
                .registeredDate(vehicleInfo.getCreatedAt().toString())
                .registeredCountry("-")
                .lastDate("-")
                .lastCountry("-")
                .policyName("Policy_ALSVIN_210")
                .version("1.0")
                .build();
    }

    public VehicleDto.VehicleGroupList getVehicleGroupList() {
        VehicleDto.VehicleGroupItems vehicleGroupItems = new VehicleDto.VehicleGroupItems(new ArrayList<>());
        VehicleDto.VehicleGroupItems[] vehicleGroupArrayItem = new VehicleDto.VehicleGroupItems[1];

        vehicleGroupArrayItem[0] = vehicleGroupItems;

        VehicleDto.VehicleGroupList vehicleGroupList = new VehicleDto.VehicleGroupList(vehicleGroupArrayItem);
        return vehicleGroupList;
    }

    public VehicleDto.PlatformList getPlatformList(Optional<String> name) {
        VehicleDto.PlatformList platformList = new VehicleDto.PlatformList(new ArrayList<>());
        return platformList;
    }

    public VehicleDto.PlatformDbDataList getPlatformDbData(String platformId) {
        VehicleDto.PlatformDbDataList platformDbDataList = new VehicleDto.PlatformDbDataList(new ArrayList<>());
        return platformDbDataList;
    }

    public VehicleDto.PlatformStat getPlatformStat(String platformId) {
        VehicleDto.PlatformStat platformStat = new VehicleDto.PlatformStat(16575, 4, 4);
        return platformStat;
    }

}
