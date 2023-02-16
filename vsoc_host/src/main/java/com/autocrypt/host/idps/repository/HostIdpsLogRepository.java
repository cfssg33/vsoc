package com.autocrypt.host.idps.repository;

import com.autocrypt.host.idps.repository.entity.HostIdpsDetectionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostIdpsLogRepository extends JpaRepository<HostIdpsDetectionLog, String> {
}
