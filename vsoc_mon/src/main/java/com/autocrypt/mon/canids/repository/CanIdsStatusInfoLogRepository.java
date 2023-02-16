package com.autocrypt.mon.canids.repository;

import com.autocrypt.mon.canids.repository.entity.CanIdsDetectionLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CanIdsStatusInfoLogRepository extends JpaRepository<CanIdsDetectionLog, Integer> {
    long countByDetectionTimeIsGreaterThan(Date detectionTime);
    List<CanIdsDetectionLog> findByOrderByDetectionTimeDesc(Pageable pageable);
}
