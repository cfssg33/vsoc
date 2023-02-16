package com.autocrypt.can.ids.model;

import com.autocrypt.can.ids.controller.dto.CanIdsLogDto;
import com.autocrypt.can.ids.repository.entity.CanIdsDetectionLog;
import com.autocrypt.can.ids.repository.entity.CanIdsMetaData;
import com.autocrypt.can.ids.repository.entity.CanIdsStatusInfoLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CanIdsLogMapper {

    CanIdsLogMapper INSTANCE = Mappers.getMapper(CanIdsLogMapper.class);

    CanIdsMetaData CanIdsMetaDataDtoToCanIdsMetaDataEntity(CanIdsLogDto.MetaDataDto metaDataDto);

    List<CanIdsDetectionLog> CanIdsDetectionDtoToCanIdsDetectionEntity(List<CanIdsLogDto.DetectionLogDto> detectionLogDto);

    CanIdsStatusInfoLog CanIdsStatusInfoDtoToCanIdsStatusInfoEntity(CanIdsLogDto.StatusInfoLogDto statusInfoLogDto);
}
