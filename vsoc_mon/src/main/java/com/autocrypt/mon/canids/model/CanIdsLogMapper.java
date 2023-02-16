package com.autocrypt.mon.canids.model;

import com.autocrypt.mon.canids.dto.CanIdsDto;
import com.autocrypt.mon.canids.repository.entity.CanIdsDetectionLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CanIdsLogMapper {
    CanIdsLogMapper INSTANCE = Mappers.getMapper(CanIdsLogMapper.class);

    @Mapping(target = "detectedTime", source = "detectionTime")
    @Mapping(target = "rawMessageData", source = "byRawMessageLength")
    @Mapping(target = "hidden", expression = "java(false)")
    CanIdsDto.CanIdsLog canIdsDetectionLogEntityToDto(CanIdsDetectionLog canIdsDetectionLog);
}
