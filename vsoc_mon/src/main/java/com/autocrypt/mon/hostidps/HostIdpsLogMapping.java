package com.autocrypt.mon.hostidps;


import com.autocrypt.mon.hostidps.repository.entity.HostIdpsDetectionLog;
import com.autocrypt.mon.monitor.dto.MonitorPanelDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HostIdpsLogMapping {

    // Generate Alarm DTO from Host IDPS Log List
    public List<MonitorPanelDto.Alarm> convertToAlarm(List<HostIdpsDetectionLog> logList) {

        List<MonitorPanelDto.Alarm> alarmList = new ArrayList<>();

        for (HostIdpsDetectionLog detectionLog: logList) {
            alarmList.add(
                    MonitorPanelDto.Alarm.builder()
                    .name(detectionLog.getRuleName())
                    .pk(detectionLog.getId())
                    .createdAt(detectionLog.getEventTime().getTime())
                    .build());
        }
        return alarmList;
    }
}
