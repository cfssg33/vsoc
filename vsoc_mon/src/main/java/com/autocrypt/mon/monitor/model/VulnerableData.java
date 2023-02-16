package com.autocrypt.mon.monitor.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VulnerableData {
    private long dangerVehicle;
    private long warningVehicle;
    private long totalVehicle;
    private long highSeverityHostLogCount;
    private long middleSeverityHostLogCount;
    private long totalSeverityHostLogCount;
    private long highSeverityCanLogCount;
    private long middleSeverityCanLogCount;
    private long totalSeverityCanLogCount;

    public double getOtl() {
        double standard1 = (this.dangerVehicle + 0.5 * this.warningVehicle) / (this.totalVehicle == 0 ? 1 : this.totalVehicle);
        double standard2 = (this.highSeverityCanLogCount + 0.5 * this.middleSeverityCanLogCount) / (this.totalSeverityCanLogCount == 0 ? 1 : this.totalSeverityCanLogCount);
        double standard3 = (this.highSeverityHostLogCount + 0.5 * this.middleSeverityHostLogCount) / (this.totalSeverityHostLogCount == 0 ? 1 : this.totalSeverityHostLogCount);
        return standard1 * 0.4 + standard2 * 0.3 + standard3 * 0.3;
    }
}