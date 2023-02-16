package com.autocrypt.collector.model;

import com.autocrypt.collector.controller.dto.VehicleInfoDTO;
import com.autocrypt.collector.repository.entity.VehicleInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarInfoMapping {
    CarInfoMapping INSTANCE = Mappers.getMapper(CarInfoMapping.class);

    VehicleInfo CarInfoDTOtoCarInfoEntity(VehicleInfoDTO.vehicleInfo vehicleInfo);
}
