package com.autocrypt.mon.singularity;

import com.autocrypt.mon.singularity.dto.SingularityDto;
import com.autocrypt.mon.singularity.service.SingularityService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/singularity")
public class SingularityController {

    @Autowired
    private final SingularityService singularityService;

    // Each device takes singularity data and responds.
    @ResponseBody
    @GetMapping("/parts")
    public SingularityDto.PartSingularityData GetPartSingularityData(@RequestBody Optional<String> vehicleId,
                                                                     @RequestBody Optional<String> ecuId) {
        return singularityService.GetPartSingularityData(vehicleId, ecuId);
    }

}
