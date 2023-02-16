package com.autocrypt.mon.hostidps.repository;

import com.autocrypt.mon.vehicleInfo.model.CountLogView;
import com.autocrypt.mon.vehicleInfo.model.VulnerableRegionView;
import com.autocrypt.mon.hostidps.repository.entity.HostIdpsDetectionLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface HostIdpsDetectionLogRepository extends JpaRepository<HostIdpsDetectionLog, String> {

    long countByEventTimeBetweenAndCheckedLogIsNull(Date logCheckTime, Date lastLoginTime);
    List<HostIdpsDetectionLog> findAllByEventTimeBetweenAndCheckedLogIsNull(Date logCheckTime, Date lastLoginTime);

    long countByEventTimeAfter(Date currentTime);
    List<HostIdpsDetectionLog> findByEventTimeAfterAndSeverityOrderByEventTimeDesc(Date date, String severity, Pageable pageable);

    @EntityGraph(attributePaths = {"hostIdpsMetadata", "hostIdpsMetadata.vehicleInfo"})
    @Query("select a from HostIdpsDetectionLog a where a.eventTime between ?1 and ?2 order by a.eventTime desc")
    List<HostIdpsDetectionLog> findDetectionLogOrderByEventTimeWithLimit(Date startDate, Date endDate, Pageable pageable);

    @EntityGraph(attributePaths = {"hostIdpsMetadata", "hostIdpsMetadata.vehicleInfo"})
    @Query("select a from HostIdpsDetectionLog a where a.ruleType = ?1 and a.eventTime between ?2 and ?3 order by a.eventTime desc")
    List<HostIdpsDetectionLog> findDetectionLogByRuleTypeOrderByEventTimeWithLimit(String ruleType, Date startDate, Date endDate, Pageable pageable);

    @Query(value = "select count(*)\n" +
                   "    from host_idps_detection_log\n" +
                   "    where event_time between ?1 and ?2", nativeQuery = true)
    int findDetectionLogCount(Date startDate, Date endDate);

    @Query(value = "select count(*)\n" +
            "    from host_idps_detection_log\n" +
            "    where rule_type = ?1 and event_time between ?2 and ?3", nativeQuery = true)
    int findDetectionLogCountByRuleType(String ruleType, Date startDate, Date endDate);

    @Query(value = "select \n" +
            "               count(distinct (case when d.severity = 'middle' then d.log_uuid end)) warning, \n" +
            "               count(distinct (case when d.severity = 'high' then d.log_uuid end)) danger, \n" +
            "               count(distinct d.log_uuid) total \n" +
            "           from \n" +
            "               host_idps_detection_log as d \n" +
            "           where d.event_time > ?1 \n", nativeQuery = true)
    CountLogView countDangerAndWarningLog(Date time);

    @Query(value = "select \n" +
            "               m.region location, \n" +
            "               count(distinct (case when d.severity = 'middle' then d.log_uuid end)) warning, \n" +
            "               count(distinct (case when d.severity = 'high' then d.log_uuid end)) danger, \n" +
            "               count(distinct d.log_uuid) total \n" +
            "           from \n" +
            "               host_idps_detection_log as d \n" +
            "           join\n" +
            "               host_idps_metadata as m \n" +
            "           where d.log_uuid = m.host_log and d.event_time > ?1 \n" +
            "           group by m.region", nativeQuery = true)
    List<VulnerableRegionView> countDangerAndWarningLogGroupByRegion(Date time);

    @Query(value = "select \n" +
            "               m.city location, \n" +
            "               count(distinct (case when d.severity = 'middle' then d.log_uuid end)) warning, \n" +
            "               count(distinct (case when d.severity = 'high' then d.log_uuid end)) danger, \n" +
            "               count(distinct d.log_uuid) total \n" +
            "           from \n" +
            "               host_idps_detection_log as d \n" +
            "           join\n" +
            "               host_idps_metadata as m \n" +
            "           where d.log_uuid = m.host_log and d.event_time > ?1 and m.region = ?2\n" +
            "           group by m.city", nativeQuery = true)
    List<VulnerableRegionView> countDangerAndWarningLogGroupByCity(Date time, String region);

    @Query(value = "select count(*)\n" +
            "    from host_idps_detection_log\n" +
            "    where event_time between ?1 and ?2", nativeQuery = true)
    int totalDetectionCount(Date startDate, Date endDate);

    @Query(value = "select count(*) / ?1\n" +
                   "    from host_idps_detection_log\n" +
                   "    where event_time between ?2 and ?3", nativeQuery = true)
    double detectionAverage(Long calDate, Date startDate, Date endDate);

    @Query(value = "select count(A.vehicle_id) from(\n" +
            "    select m.vehicle_id\n" +
            "    from host_idps_detection_log as d\n" +
            "    join host_idps_metadata as m\n" +
            "    where d.log_uuid = m.host_log and (event_time between ?1 and ?2)\n" +
            "    group by m.vehicle_id) as A", nativeQuery = true)
    int countDetectionVehicleId(Date startDate, Date endDate);

    @Query(value = "select\n" +
            "        rule_name name, count(rule_name) cnt\n" +
            "    from host_idps_detection_log\n" +
            "    where event_time between ?1 and ?2\n" +
            "    group by rule_name\n" +
            "    order by count(rule_name) desc\n" +
            "    limit 5", nativeQuery = true)
    List<HostIdpsTopFiveRule> countTopFiveRules(Date startDate, Date endDate);
}
