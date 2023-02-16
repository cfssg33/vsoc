package com.autocrypt.mon.monitor;

import com.autocrypt.mon.common.exception.MonIdpsException;
import com.autocrypt.mon.monitor.dto.MonitorLogCheckDto;
import com.autocrypt.mon.monitor.dto.MonitorPanelDto;
import com.autocrypt.mon.monitor.model.VulnerableResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monitor")
public class MonitorController {

    private final MonitorService monitorService;

    @GetMapping("/monitorpanel")
    public MonitorPanelDto.MonitorPanelResp getMonitorPanel() throws MonIdpsException {
        // Request Overall Statistics for right panel of main page
        // return overallThreatLevel, totalVehicles, vehiclePlatformType, detectedCanLogs, detectedHostLogs
        return this.monitorService.getMonitorPanel();
    }

    @GetMapping("/vulnerable/region")
    public List<VulnerableResult> getVulnerableRegionData() {
        // Request All regions' vulnerable Data
        return this.monitorService.getVulnerableRegion();
    }

    @GetMapping("/vulnerable/city")
    public List<VulnerableResult> getVulnerable(@RequestParam String region) {
        // Request All city' vulnerable Data of specific region
        return this.monitorService.getVulnerableCity(region);
    }

    @GetMapping("/log/uncheck/count")
    public Long getUncheckLogCount (@RequestParam String accountId) throws MonIdpsException {
        // Request Counting number of unchecked Log since last access time
        return this.monitorService.getUnCheckLogCount(accountId);
    }

    @PostMapping("/log/check")
    public ResponseEntity<?> setCheckLog (@RequestBody MonitorLogCheckDto monitorLogCheckDto) throws MonIdpsException {
        // Mark that the user checked the all logs
        this.monitorService.setCheckLog(monitorLogCheckDto.getAccountId());
        return ResponseEntity.ok("{\"result\" : \"ok\"}");
    }

    @GetMapping("/server/status")
    public ResponseEntity<?> getServerHealthStatus (@RequestParam @Nullable String lastAccessTime
                                                    ,@RequestParam Boolean isFirstAccess) throws MonIdpsException {
        // Request the status of server's health (check if the server is turned off and reloaded)
        String result = this.monitorService.getServerHealthStatus(lastAccessTime, isFirstAccess);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/detectionLog/hour")
    public Long getHoursDetctionLog (@RequestParam String accountId,
                                     @RequestParam @Nullable String lastAccessTime) throws Exception {
        // Request Counting number of Detection Logs for 1 hour
        return this.monitorService.getHoursDetctionLog(lastAccessTime, accountId);
    }

}
