package com.autocrypt.mon.singularity.dto;

import lombok.*;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SingularityDto {

    @Getter
    @AllArgsConstructor
    public static class PartSingularityData{
        private final Number[] lineData = new Number[62];
        private final Number[] barData = new Number[62];
    }

}
