package com.autocrypt.mon.canids.repository;

import com.autocrypt.mon.canids.dto.CanIdsDto;
import com.autocrypt.mon.canids.repository.entity.CanIdsDetectionLog;
import com.autocrypt.mon.vehicleInfo.model.CountLogView;
import com.autocrypt.mon.vehicleInfo.model.VulnerableRegionView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CanIdsDetectionLogRepository extends JpaRepository<CanIdsDetectionLog, Integer> {

    long countByDetectionTimeBetweenAndCheckedLogIsNull(Date logCheckTime, Date lastLoginTime);
    List<CanIdsDetectionLog> findAllByDetectionTimeBetweenAndCheckedLogIsNull(Date logCheckTime, Date lastLoginTime);

    long countByDetectionTimeAfter(Date currentTime);
    List<CanIdsDetectionLog> findByDetectionTimeAfterAndSeverityOrderByDetectionTimeDesc (Date date, String severity, Pageable pageable);

    @EntityGraph(attributePaths = {"canIdsMetaData", "canIdsMetaData.vehicleInfo"})
    @Query("select a from CanIdsDetectionLog a where a.detectionTime between ?1 and ?2 order by a.detectionTime desc")
    List<CanIdsDetectionLog> findDetectionLogOrderByDetectionTimeWithLimit(Date startDate, Date endDate, Pageable pageable);

    @Query(value = "select count(*)\n" +
                   "    from host_idps_detection_log\n" +
                   "    where event_time between ?1 and ?2", nativeQuery = true)
    int findDetectionLogCount(Date startDate, Date endDate);

    @Query(value = "select \n" +
            "               count(distinct (case when d.severity = 'middle' then d.id end)) warning, \n" +
            "               count(distinct (case when d.severity = 'high' then d.id end)) danger, \n" +
            "               count(distinct d.id) total \n" +
            "           from \n" +
            "               can_ids_detection_log as d \n" +
            "           where d.detection_time > ?1 \n", nativeQuery = true)
    CountLogView countDangerAndWarningLog(Date time);

    @Query(value = "select \n" +
            "               m.region, \n" +
            "               count(distinct (case when d.severity = 'middle' then d.id end)) warning, \n" +
            "               count(distinct (case when d.severity = 'high' then d.id end)) danger, \n" +
            "               count(distinct d.id) total \n" +
            "           from \n" +
            "               can_ids_detection_log as d \n" +
            "           join\n" +
            "               can_ids_meta_data as m \n" +
            "           where d.match_id = m.id and d.detection_time > ?1 \n" +
            "           group by m.region", nativeQuery = true)
    List<VulnerableRegionView> countDangerAndWarningLogGroupByRegion(Date time);

    @Query(value = "select \n" +
            "               m.city, \n" +
            "               count(distinct (case when d.severity = 'middle' then d.id end)) warning, \n" +
            "               count(distinct (case when d.severity = 'high' then d.id end)) danger, \n" +
            "               count(distinct d.id) total \n" +
            "           from \n" +
            "               can_ids_detection_log as d \n" +
            "           join\n" +
            "               can_ids_meta_data as m \n" +
            "           where d.match_id = m.id and d.detection_time > ?1 and m.region = ?2\n" +
            "           group by m.city", nativeQuery = true)
    List<VulnerableRegionView> countDangerAndWarningLogGroupByCity(Date time, String region);

    @Query(value = "select count(*)\n" +
                   "    from can_ids_detection_log\n" +
                   "    where detection_time between ?1 and ?2", nativeQuery = true)
    int totalDetectionCount(Date startDate, Date endDate);

    @Query(value = "select count(*) / ?1\n" +
                   "    from can_ids_detection_log\n" +
                   "    where detection_time between ?2 and ?3", nativeQuery = true)
    double detectionAverage(Long calDate, Date startDate, Date endDate);

    @Query(value = "select count(A.vehicle_id) from(\n" +
                   "    select m.vehicle_id\n" +
                   "    from can_ids_detection_log as d\n" +
                   "    join can_ids_meta_data as m\n" +
                   "    where d.match_id = m.id and (detection_time between ?1 and ?2)\n" +
                   "    group by m.vehicle_id) as A", nativeQuery = true)
    int countDetectionVehicleId(Date startDate, Date endDate);

    @Query(value = "select\n" +
                   "        by_violation_rule_name name, count(by_violation_rule_name) cnt\n" +
                   "    from can_ids_detection_log\n" +
                   "    where detection_time between ?1 and ?2\n" +
                   "    group by by_violation_rule_name\n" +
                   "    order by count(by_violation_rule_name) desc\n" +
                   "    limit 5", nativeQuery = true)
    List<CanIdsTopFiveRule> countTopFiveRules(Date startDate, Date endDate);
}
