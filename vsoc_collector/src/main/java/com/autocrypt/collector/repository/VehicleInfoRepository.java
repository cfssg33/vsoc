package com.autocrypt.collector.repository;

import com.autocrypt.collector.repository.entity.VehicleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleInfoRepository extends JpaRepository<VehicleInfo, String> {
    boolean existsByTuidEquals(String tuid);

    Optional<VehicleInfo> findByTuidEquals(String tuid);

}
