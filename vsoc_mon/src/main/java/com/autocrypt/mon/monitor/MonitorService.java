package com.autocrypt.mon.monitor;

import com.autocrypt.mon.account.AccountRepository;
import com.autocrypt.mon.account.entity.Account;
import com.autocrypt.mon.apilog.ApiLogRepository;
import com.autocrypt.mon.apilog.entity.ApiLog;
import com.autocrypt.mon.canids.CanIdsLogMapping;
import com.autocrypt.mon.canids.repository.CanIdsDetectionLogRepository;
import com.autocrypt.mon.canids.repository.entity.CanIdsDetectionLog;
import com.autocrypt.mon.divisions.Divisions;
import com.autocrypt.mon.common.exception.MonIdpsException;
import com.autocrypt.mon.hostidps.HostIdpsLogMapping;
import com.autocrypt.mon.monitor.model.VulnerableData;
import com.autocrypt.mon.vehicleInfo.model.CountLogGroupByLocationView;
import com.autocrypt.mon.vehicleInfo.model.CountLogView;
import com.autocrypt.mon.vehicleInfo.model.VulnerableRegionView;
import com.autocrypt.mon.hostidps.repository.HostIdpsDetectionLogRepository;
import com.autocrypt.mon.hostidps.repository.entity.HostIdpsDetectionLog;
import com.autocrypt.mon.monitor.dto.MonitorPanelDto;
import com.autocrypt.mon.monitor.model.VulnerableResult;
import com.autocrypt.mon.vehicleInfo.repository.VehicleInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonitorService {

    private final HostIdpsDetectionLogRepository hostIdpsDetectionLogRepository;
    private final CanIdsDetectionLogRepository canIdsDetectionLogRepository;
    private final VehicleInfoRepository vehicleInfoRepository;

    private final CanIdsLogMapping canIdsLogMapping;
    private final HostIdpsLogMapping hostIdpsLogMapping;

    private final AccountRepository accountRepository;
    private final ApiLogRepository apiLogRepository;

    public MonitorPanelDto.MonitorPanelResp getMonitorPanel() throws MonIdpsException {

        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -1);

        Date agoOneHourDate = calendar.getTime();

        MonitorPanelDto.DetectedCanLogs DetectedCanLogs = this.detectedAfterCanLogs(agoOneHourDate);
        MonitorPanelDto.DetectedHostLogs detectedHostLogs = this.detectedAfterHostLogs(agoOneHourDate);
        MonitorPanelDto.VehiclePlatformType vehiclePlatformType  = this.findVehicleAndPolicyCount();

        VulnerableResult vulnerableResult = getVulnerableTotal();

        MonitorPanelDto.TotalVehicles totalVehicles = this.totalVehicles(vulnerableResult);
        MonitorPanelDto.OverallThreatLevel overallThreatLevel =  this.getTotalOtl(vulnerableResult.getOtl());

        return MonitorPanelDto.MonitorPanelResp.builder()
                .overallThreatLevel(overallThreatLevel)
                .totalVehicles(totalVehicles)
                .vehiclePlatformType(vehiclePlatformType)
                .detectedCanLogs(DetectedCanLogs)
                .detectedHostLogs(detectedHostLogs).build();
    }


    private MonitorPanelDto.DetectedHostLogs detectedAfterHostLogs (Date date) {

        long count = hostIdpsDetectionLogRepository.countByEventTimeAfter(date);

        List<HostIdpsDetectionLog>  hostIdpsDetectionLogs =
                hostIdpsDetectionLogRepository.findByEventTimeAfterAndSeverityOrderByEventTimeDesc(date,"high", PageRequest.of(0, 3));

        List<MonitorPanelDto.Alarm> hostIdpsAlarms =
                hostIdpsLogMapping.convertToAlarm(hostIdpsDetectionLogs);

        return MonitorPanelDto.DetectedHostLogs.builder()
                .count(count)
                .alarms(hostIdpsAlarms)
                .build();
    }

    private MonitorPanelDto.DetectedCanLogs detectedAfterCanLogs (Date date) throws MonIdpsException {

        long count = canIdsDetectionLogRepository.countByDetectionTimeAfter(date);

        List<CanIdsDetectionLog> hostIdpsDetectionLogs =
                canIdsDetectionLogRepository.findByDetectionTimeAfterAndSeverityOrderByDetectionTimeDesc(date,"high", PageRequest.of(0, 3));

        List<MonitorPanelDto.Alarm> canIdsAlarms = canIdsLogMapping.convertToAlarm(hostIdpsDetectionLogs);

        return MonitorPanelDto.DetectedCanLogs.builder()
                .count(count)
                .alarms(canIdsAlarms)
                .build();
    }

    private MonitorPanelDto.VehiclePlatformType findVehicleAndPolicyCount () {
        return MonitorPanelDto.VehiclePlatformType.builder()
                .count(17)
                .appliedSecurityPolicy(8)
                .build();
    }

    private MonitorPanelDto.OverallThreatLevel getTotalOtl (double otl) {
        return   MonitorPanelDto.OverallThreatLevel.builder()
                .count((int)otl)
                .build();
    }

    private MonitorPanelDto.TotalVehicles totalVehicles (VulnerableResult vulnerableResult) {

        long count = vehicleInfoRepository.count();
        long danger = vulnerableResult.getDanger();
        long warning = vulnerableResult.getWarning();

        return MonitorPanelDto.TotalVehicles.builder()
                .count(count)
                .danger(danger)
                .warning(warning)
                .build();
    }

    public VulnerableResult getVulnerableTotal() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1);

        return convertVulnerableDataToVulnerableDTO(
                vehicleInfoRepository.getDangerAndWarningVehicleCount(),
                hostIdpsDetectionLogRepository.countDangerAndWarningLog(calendar.getTime()),
                canIdsDetectionLogRepository.countDangerAndWarningLog(calendar.getTime())
        );
    }

    public List<VulnerableResult> getVulnerableRegion() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1);

        return convertGroupedVulnerableDataToVulnerableDTO(
                vehicleInfoRepository.getDangerAndWarningVehicleCountGroupByRegion(),
                hostIdpsDetectionLogRepository.countDangerAndWarningLogGroupByRegion(calendar.getTime()),
                canIdsDetectionLogRepository.countDangerAndWarningLogGroupByRegion(calendar.getTime()),
                Divisions.FIRSTDIVISION.getList()
        );

    }

    public List<VulnerableResult> getVulnerableCity(String region) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1);

        return convertGroupedVulnerableDataToVulnerableDTO(
                vehicleInfoRepository.getDangerAndWarningVehicleCountGroupByCity(region),
                hostIdpsDetectionLogRepository.countDangerAndWarningLogGroupByCity(calendar.getTime(), region),
                canIdsDetectionLogRepository.countDangerAndWarningLogGroupByCity(calendar.getTime(), region),
                Divisions.valueOf(region).getList()
        );
    }

    private VulnerableResult convertVulnerableDataToVulnerableDTO(CountLogView dangerAndWarningVehicleCount,
                                                                  CountLogView countDangerAndWarningHostLog,
                                                                  CountLogView countDangerAndWarningCanLog) {

        VulnerableData vulnerableData = VulnerableData.builder()
                .warningVehicle(dangerAndWarningVehicleCount.getWarning())
                .dangerVehicle(dangerAndWarningVehicleCount.getDanger())
                .totalVehicle(dangerAndWarningVehicleCount.getTotal())
                .highSeverityHostLogCount(countDangerAndWarningHostLog.getDanger())
                .middleSeverityHostLogCount(countDangerAndWarningHostLog.getWarning())
                .totalSeverityHostLogCount(countDangerAndWarningHostLog.getTotal())
                .highSeverityCanLogCount(countDangerAndWarningCanLog.getDanger())
                .middleSeverityCanLogCount(countDangerAndWarningCanLog.getWarning())
                .totalSeverityCanLogCount(countDangerAndWarningCanLog.getTotal())
                .build();

        return VulnerableResult.builder()
                .location("Total")
                .otl(Math.round(vulnerableData.getOtl() * 100))
                .danger(vulnerableData.getDangerVehicle())
                .warning(vulnerableData.getWarningVehicle())
                .build();
    }

    private List<VulnerableResult> convertGroupedVulnerableDataToVulnerableDTO(List<CountLogGroupByLocationView> dangerAndWarningVehicleCountGroupByRegion,
                                                                               List<VulnerableRegionView> countDangerAndWarningHostLogGroupByCity,
                                                                               List<VulnerableRegionView> countDangerAndWarningCanLogGroupByRegion,
                                                                               List<String> groupKeyList) {

        Map<String, CountLogGroupByLocationView> vehicleCountMap = new HashMap<>();
        dangerAndWarningVehicleCountGroupByRegion.forEach(data -> vehicleCountMap.put(data.getLocation(), data));

        Map<String, VulnerableRegionView> hostLogCountMap = new HashMap<>();
        countDangerAndWarningHostLogGroupByCity.forEach(data -> hostLogCountMap.put(data.getLocation(), data));

        Map<String, VulnerableRegionView> canLogCountMap = new HashMap<>();
        countDangerAndWarningCanLogGroupByRegion.forEach(data -> canLogCountMap.put(data.getLocation(), data));


        Map<String, VulnerableData> vulnerableDataMap = new HashMap<>();
        groupKeyList.forEach(key -> {
            VulnerableData vulnerableData = new VulnerableData();
            if(vehicleCountMap.containsKey(key)) {
                vulnerableData.setWarningVehicle(vehicleCountMap.get(key).getWarning());
                vulnerableData.setDangerVehicle(vehicleCountMap.get(key).getDanger());
                vulnerableData.setTotalVehicle(vehicleCountMap.get(key).getTotal());
            }
            if(hostLogCountMap.containsKey(key)) {
                vulnerableData.setHighSeverityHostLogCount(hostLogCountMap.get(key).getDanger());
                vulnerableData.setMiddleSeverityHostLogCount(hostLogCountMap.get(key).getWarning());
                vulnerableData.setTotalSeverityHostLogCount(hostLogCountMap.get(key).getTotal());
            }
            if(canLogCountMap.containsKey(key)) {
                vulnerableData.setHighSeverityCanLogCount(canLogCountMap.get(key).getDanger());
                vulnerableData.setMiddleSeverityCanLogCount(canLogCountMap.get(key).getWarning());
                vulnerableData.setTotalSeverityCanLogCount(canLogCountMap.get(key).getTotal());
            }
            vulnerableDataMap.put(key, vulnerableData);
        });

        return vulnerableDataMap.entrySet().stream()
                .map(vData -> VulnerableResult.builder()
                        .location(vData.getKey())
                        .otl(Math.round(vData.getValue().getOtl() * 100))
                        .danger(vData.getValue().getDangerVehicle())
                        .warning(vData.getValue().getWarningVehicle())
                        .build())
                .collect(Collectors.toList());
    }

    private Date getCheckLogTime (Account accountInfo) {
        Date convertDate;
        if (accountInfo.getLogCheckTime() == null) {
            convertDate = Timestamp.valueOf(accountInfo.getLastAccessTime() == null ? accountInfo.getCreatedDate() : accountInfo.getLastAccessTime().minusHours(1));
        }else {
            convertDate = Timestamp.valueOf(accountInfo.getLogCheckTime());
        }

        return convertDate;
    }

    private Date getLastAccessLogTime (Account accountInfo) throws MonIdpsException {
        Date convertDate =  Optional.ofNullable(Timestamp.valueOf(accountInfo.getLastAccessTime()))
                .orElseThrow(() -> new MonIdpsException("Login is not logged"));

        return convertDate;
    }

    public Long getUnCheckLogCount (String accountId) throws MonIdpsException {

        Account accountInfo = Optional.ofNullable( this.accountRepository.findByAccountId(accountId))
                .orElseThrow(() -> new MonIdpsException("Account information was not obtained."));

        Date checkLogTime = this.getCheckLogTime(accountInfo);
        Date lastAccessLog = this.getLastAccessLogTime(accountInfo);

        long canLogCount =  this.canIdsDetectionLogRepository.countByDetectionTimeBetweenAndCheckedLogIsNull(checkLogTime, lastAccessLog);
        long hostLogCount = this.hostIdpsDetectionLogRepository.countByEventTimeBetweenAndCheckedLogIsNull(checkLogTime, lastAccessLog);

        return canLogCount + hostLogCount;
    }

    @Transient
    public void setCheckLog (String accountId) throws MonIdpsException {
        Account accountInfo = Optional.ofNullable( this.accountRepository.findByAccountId(accountId))
                .orElseThrow(() -> new MonIdpsException("Account information was not obtained."));

        Date checkLogTime = this.getCheckLogTime(accountInfo);
        Date lastAccessLog = this.getLastAccessLogTime(accountInfo);

        List<CanIdsDetectionLog> canIdsDetectionLogs = this.canIdsDetectionLogRepository.findAllByDetectionTimeBetweenAndCheckedLogIsNull(checkLogTime, lastAccessLog);
        canIdsDetectionLogs.stream().forEach((log) -> log.setCheckedLog("checked"));
        this.canIdsDetectionLogRepository.saveAll(canIdsDetectionLogs);

        List<HostIdpsDetectionLog> hostIdpsDetectionLogs = this.hostIdpsDetectionLogRepository.findAllByEventTimeBetweenAndCheckedLogIsNull(checkLogTime, lastAccessLog);
        hostIdpsDetectionLogs.stream().forEach((log) -> log.setCheckedLog("checked"));
        this.hostIdpsDetectionLogRepository.saveAll(hostIdpsDetectionLogs);

        accountInfo.setLogCheckTime(LocalDateTime.now());
        this.accountRepository.save(accountInfo);
    }

    public String getServerHealthStatus (String lastAccessTime, Boolean isFirstAccess) throws MonIdpsException {

        List<ApiLog> apiLog = new ArrayList<>();
        if (isFirstAccess == false) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.parse(lastAccessTime, formatter);

            apiLog = this.apiLogRepository.findAllByUrlAndRequestAndCreatedTimeBetween("/", "Service Start", date, LocalDateTime.now());
        }

        String resultMsg = "{\"result\" : \"Normal\"}";;
        if (apiLog.size() > 0 && isFirstAccess == false) {
            resultMsg = "{\"result\" : \"Restarted\"}";
        }

        return resultMsg;
    }

    public Long getHoursDetctionLog (String lastAccessTime, String accountId) throws Exception {

        Date date = new Date();
        if (lastAccessTime.equals("null")) {
            Account accountInfo = Optional.ofNullable( this.accountRepository.findByAccountId(accountId))
                    .orElseThrow(() -> new MonIdpsException("Account information was not obtained."));

            date = Date.from(accountInfo.getLastAccessTime().atZone(ZoneId.systemDefault()).toInstant());

        } else {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = transFormat.parse(lastAccessTime);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, -1);

        Date currentTime = cal.getTime();

        long CanLogHoursCount = this.canIdsDetectionLogRepository.countByDetectionTimeAfter(currentTime);
        long hostLogHoursCount = this.hostIdpsDetectionLogRepository.countByEventTimeAfter(currentTime);

        return CanLogHoursCount + hostLogHoursCount;
    }
}
