package com.autocrypt.mon.vehicleInfo.model;

public interface CountLogGroupByLocationView {
    String getLocation();
    Long getDanger();
    Long getWarning();
    Long getTotal();
}
