package com.autocrypt.mon.statistics;

import com.autocrypt.mon.statistics.dto.StatisticsDto;
import com.autocrypt.mon.statistics.service.StatisticsService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/trends")
public class StatisticsController {

    @Autowired
    private final StatisticsService statisticsService;

    //Reponse the overall trend data
    @ResponseBody
    @GetMapping("/overall")
    public StatisticsDto.OverallTrendsData GetOverallTrendsData() {
        return statisticsService.GetOverallTrendsData();
    }

    //The trends data for each region is returned.
    @ResponseBody
    @GetMapping("/region")
    public StatisticsDto.RegionTrendsData GetRegionTrendsData(@RequestBody String region,
                                                              @RequestBody String city) {
        return statisticsService.GetRegionTrendsData(region, city);
    }

    //The trends data for each vehicle is returned.
    @ResponseBody
    @GetMapping("/vehicle")
    public StatisticsDto.VehicleTrendsData GetVehicleTrendsData(@RequestBody Optional<String> vehicleId) {
        return statisticsService.GetVehicleTrendsData(vehicleId);
    }

    //The trends data for each part is responded.
    @ResponseBody
    @GetMapping("/parts")
    public StatisticsDto.PartsTrendsData GetPartsTrendsData(@RequestBody Optional<String> platformName,
                                                            @RequestBody Number startDate,
                                                            @RequestBody Optional<Number> endDate) {
        return statisticsService.GetPartsTrendsData(platformName, startDate, endDate);
    }

}
