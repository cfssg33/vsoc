package com.autocrypt.mon.vehicleInfo.repository;

import com.autocrypt.mon.vehicleInfo.model.CountLogGroupByLocationView;
import com.autocrypt.mon.vehicleInfo.model.CountLogView;
import com.autocrypt.mon.vehicleInfo.repository.entity.VehicleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleInfoRepository extends JpaRepository<VehicleInfo, String> {
    @Query(value = "select\n" +
            "    count(distinct (case when v.seriousness = 'DANGER' then v.tuid end)) danger, \n" +
            "    count(distinct (case when v.seriousness = 'WARNING' then v.tuid end)) warning, \n" +
            "    count(distinct v.tuid) total \n" +
            "from vehicle_info_from_chang_an as v\n", nativeQuery = true)
    CountLogView getDangerAndWarningVehicleCount();

    @Query(value = "select\n" +
            "    v.region location, " +
            "    count(distinct (case when v.seriousness = 'DANGER' then v.tuid end)) danger, \n" +
            "    count(distinct (case when v.seriousness = 'WARNING' then v.tuid end)) warning, \n" +
            "    count(distinct v.tuid) total \n" +
            "from vehicle_info_from_chang_an as v\n" +
            "group by v.region", nativeQuery = true)
    List<CountLogGroupByLocationView> getDangerAndWarningVehicleCountGroupByRegion();

    @Query(value = "select\n" +
            "    v.city location , " +
            "    count(distinct (case when v.seriousness = 'DANGER' then v.tuid end)) danger, \n" +
            "    count(distinct (case when v.seriousness = 'WARNING' then v.tuid end)) warning, \n" +
            "    count(distinct v.tuid) total \n" +
            "from vehicle_info_from_chang_an as v \n" +
            "where v.region = ?1 \n" +
            "group by v.city", nativeQuery = true)
    List<CountLogGroupByLocationView> getDangerAndWarningVehicleCountGroupByCity(String region);

    Optional<VehicleInfo> findByVinEquals(String vin);

}
