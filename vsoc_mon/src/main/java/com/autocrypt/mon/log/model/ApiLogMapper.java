package com.autocrypt.mon.log.model;

import com.autocrypt.mon.apilog.entity.ApiLog;
import com.autocrypt.mon.log.dto.ApiLogDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiLogMapper {
    ApiLogMapper INSTANCE = Mappers.getMapper(ApiLogMapper.class);

    @Mapping(target = "processingTime", expression = "java(apiLog.getProcessingTimeString())")
    @Mapping(target = "createdTime", expression = "java(apiLog.getCreatedTime().toString().replace('T', ' '))")
    ApiLogDto apiLogEntityToApiLogDTO(ApiLog apiLog);
}
