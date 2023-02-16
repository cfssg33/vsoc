package com.autocrypt.mon.singularity.service;

import com.autocrypt.mon.singularity.dto.SingularityDto;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class SingularityService {

    public SingularityDto.PartSingularityData GetPartSingularityData(Optional<String> vehicleId,
                                                                     Optional<String> ecuId) {
        SingularityDto.PartSingularityData partSingularityData = new SingularityDto.PartSingularityData();
        return partSingularityData;
    }

}
