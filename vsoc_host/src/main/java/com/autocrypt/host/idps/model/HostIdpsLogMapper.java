package com.autocrypt.host.idps.model;

import com.autocrypt.host.idps.controller.dto.HostIdpsLogDTO;
import com.autocrypt.host.idps.repository.entity.HostIdpsMetadata;
import com.autocrypt.host.idps.repository.entity.HostIdpsStatusLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HostIdpsLogMapper {
    HostIdpsLogMapper INSTANCE = Mappers.getMapper(HostIdpsLogMapper.class);

    @Mapping(target = "hostIdpsDetectionLogs", source = "detectionLogs")
    HostIdpsMetadata toHostIdpsMetadataEntity (HostIdpsLogDTO.HostIdpsReq hostIdpsReq);

    //@Mapping(target = "hostIdpsMetadata", source = "statusLogs")
    HostIdpsStatusLog toHostIdpsStatusLogEntity (HostIdpsLogDTO.HostIdpsReq.statusLog statusLog);
}
