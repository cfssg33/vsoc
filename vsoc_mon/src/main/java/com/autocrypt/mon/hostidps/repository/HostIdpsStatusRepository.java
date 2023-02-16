package com.autocrypt.mon.hostidps.repository;

import com.autocrypt.mon.hostidps.repository.entity.HostIdpsStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostIdpsStatusRepository extends JpaRepository<HostIdpsStatusLog, String> {
}
