package com.autocrypt.mon.vehicleInfo.model;

public interface VulnerableRegionView {
    String getLocation();
    Long getWarning();
    Long getDanger();
    Long getTotal();
}
