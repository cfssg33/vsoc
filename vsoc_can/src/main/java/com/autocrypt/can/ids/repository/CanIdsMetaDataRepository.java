package com.autocrypt.can.ids.repository;

import com.autocrypt.can.ids.repository.entity.CanIdsMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanIdsMetaDataRepository extends JpaRepository<CanIdsMetaData, String> {
}
