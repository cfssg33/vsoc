package com.autocrypt.host.idps.repository;

import com.autocrypt.host.idps.repository.entity.HostIdpsMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostIdpsMetaDataRepository extends JpaRepository<HostIdpsMetadata, Long> {
}
