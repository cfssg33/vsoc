package com.autocrypt.mon.statistics.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticsDto {

    @Getter
    @AllArgsConstructor
    public static class OverallTrendsData{
        private final Number[] lineData;
        private final Number[] barData;
    }

    @Getter
    @AllArgsConstructor
    public static class RegionTrendsData{
        private final Number[] lineData;
        private final Number[] barData;
    }

    @Getter
    @AllArgsConstructor
    public static class VehicleTrendsData{
        private final Number[] lineData;
        private final Number[] barData;
    }

    @Getter
    @AllArgsConstructor
    public static class PartsTrendsData{
        private final Number[] dayData;
    }

}
