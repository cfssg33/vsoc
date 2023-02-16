package com.autocrypt.host.idps.repository;

import com.autocrypt.host.idps.repository.entity.HostIdpsStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostIdpsStatusRepository extends JpaRepository<HostIdpsStatusLog, String> {
}
