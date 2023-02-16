package com.autocrypt.can.common;

import com.autocrypt.can.ids.controller.dto.CanIdsLogDto;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@NoArgsConstructor
public class CanIdsMetaDataCreate {

    // Setting CAN IDS Log Metadata
    public static CanIdsLogDto.MetaDataDto setMetaData(String vehicleId, String region, String city) throws ParseException {
        CanIdsLogDto.MetaDataDto metaDataDto = new CanIdsLogDto.MetaDataDto();

        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String timeStr = timeFormat.format(currentDate);

        metaDataDto.setId(CanIdsUtil.uuid());
        metaDataDto.setCreateTime(timeFormat.parse(timeStr));
        metaDataDto.setVehicleId(vehicleId);
        metaDataDto.setRegion(region);
        metaDataDto.setCity(city);

        return metaDataDto;
    }

}
