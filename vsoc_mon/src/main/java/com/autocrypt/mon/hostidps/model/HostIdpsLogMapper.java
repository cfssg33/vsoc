package com.autocrypt.mon.hostidps.model;

import com.autocrypt.mon.hostidps.dto.HostIdpsDto;
import com.autocrypt.mon.hostidps.repository.entity.HostIdpsDetectionLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HostIdpsLogMapper {
    HostIdpsLogMapper INSTANCE = Mappers.getMapper(HostIdpsLogMapper.class);

    @Mapping(target = "detectedTime", source = "eventTime")
    @Mapping(target = "hidden", expression = "java(false)")
    @Mapping(target = "logType", source = "ruleType")
    HostIdpsDto.HostIdpsLog hostIdpsDetectionLogEntityToDto(HostIdpsDetectionLog hostIdpsDetectionLog);
}
