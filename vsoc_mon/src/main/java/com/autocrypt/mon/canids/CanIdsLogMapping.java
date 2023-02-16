package com.autocrypt.mon.canids;

import com.autocrypt.mon.canids.repository.entity.CanIdsDetectionLog;
import com.autocrypt.mon.common.exception.MonIdpsException;
import com.autocrypt.mon.monitor.dto.MonitorPanelDto;
import com.autocrypt.mon.policy.CanPolicy;
import com.autocrypt.mon.policy.dto.CanPolicyDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CanIdsLogMapping {
    public static CanPolicyDto.MessageInfo getMessageInfo(int busNum, int messageId) {
        String busName = findBusName(busNum);

        HashMap<String, CanPolicyDto.MessageInfo> messageInfoMaps = CanPolicy.canPolicyDto.getBody().getMessageInfo();
        Map<String, CanPolicyDto.MessageInfo> messageInfoMap = messageInfoMaps.entrySet().stream()
                .filter((obj) ->
                        obj.getValue().getBusName().equals(busName) == true &&
                                obj.getValue().getMessageId() == messageId)
                .collect(Collectors.toMap(obj -> obj.getKey(), obj -> obj.getValue()));

        CanPolicyDto.MessageInfo info = new CanPolicyDto.MessageInfo();
        for (Map.Entry<String, CanPolicyDto.MessageInfo> entrySet : messageInfoMap.entrySet()) {
            if (entrySet.getValue() instanceof CanPolicyDto.MessageInfo) {
                info = (CanPolicyDto.MessageInfo) entrySet.getValue();
            }
        }
        return info;
    }

    public static String getSignalId (int busNum, int startBit, int messageId) {

        String busName = findBusName(busNum);

        HashMap<String, CanPolicyDto.SignalInfo> signalInfo = CanPolicy.canPolicyDto.getBody().getSignalInfo();
        String signalName = signalInfo.entrySet().stream()
                .filter((obj) ->
                        obj.getValue().getBusName().equals(busName) == true &&
                                obj.getValue().getSignalStartBit() == startBit &&
                                obj.getValue().getMessageId() == messageId)
                .map((obj) -> obj.getValue().getSignalName())
                .collect(Collectors.joining(","));

        return signalName.substring(signalName.lastIndexOf("/") +1);
    }

    public static String findBusName (int busNum) {

        HashMap<String, CanPolicyDto.BusInfo> busInfo = CanPolicy.canPolicyDto.getHeader().getBusInfo();

        return busInfo.entrySet().stream()
                .filter((obj) -> obj.getValue().getBusNumber() == busNum)
                .map((info) -> info.getValue().getBusName())
                .collect(Collectors.joining(","));
    }

    public List<MonitorPanelDto.Alarm> convertToAlarm(List<CanIdsDetectionLog> logList) throws MonIdpsException{
        List<MonitorPanelDto.Alarm> alarmList = new ArrayList<>();
        for (CanIdsDetectionLog log: logList) {
            String signalId = getSignalId(
                    log.getByCanBusNum(),
                    log.getSignalStartBit(),
                    log.getCanId());
            MonitorPanelDto.Alarm alarm = new MonitorPanelDto.Alarm(signalId, Integer.toString(log.getId()), log.getDetectionTime().getTime());
            alarmList.add(alarm);
        }
        return alarmList;
    }
}
