package com.autocrypt.can.ids.repository;

import com.autocrypt.can.ids.repository.entity.CanIdsDetectionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanIdsDetectionLogRepository extends JpaRepository<CanIdsDetectionLog, Integer> {
}
