package com.autocrypt.mon.statistics.service;

import com.autocrypt.mon.statistics.dto.StatisticsDto;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class StatisticsService {

    public StatisticsDto.OverallTrendsData GetOverallTrendsData() {
        Number[] lineData = new Number[31];
        Number[] barData = new Number[31];

        StatisticsDto.OverallTrendsData overallTrendsData = new StatisticsDto.OverallTrendsData(lineData, barData);

        return overallTrendsData;
    }

    public StatisticsDto.RegionTrendsData GetRegionTrendsData(String region, String city) {
        Number[] lineData = new Number[31];
        Number[] barData = new Number[31];
        StatisticsDto.RegionTrendsData regionTrendsData = new StatisticsDto.RegionTrendsData(lineData, barData);

        return regionTrendsData;
    }

    public StatisticsDto.VehicleTrendsData GetVehicleTrendsData(Optional<String> vehicleId) {
        Number[] lineData = new Number[62];
        Number[] barData = new Number[62];
        StatisticsDto.VehicleTrendsData vehicleTrendsData = new StatisticsDto.VehicleTrendsData(lineData, barData);

        return vehicleTrendsData;
    }

    public StatisticsDto.PartsTrendsData GetPartsTrendsData(Optional<String> platformName,
                                                            Number startDate,
                                                            Optional<Number> endDate) {
        int length = (endDate.get().intValue() - startDate.intValue()) + 1;
        StatisticsDto.PartsTrendsData partsTrendsData = new StatisticsDto.PartsTrendsData(new Number[length]);
        return partsTrendsData;
    }

}
