package com.autocrypt.mon.monitor.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MonitorLogCheckDto {

    @NotNull
    private String accountId;
}
