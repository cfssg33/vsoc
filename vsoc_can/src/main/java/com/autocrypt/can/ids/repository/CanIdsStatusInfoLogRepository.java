package com.autocrypt.can.ids.repository;

import com.autocrypt.can.ids.repository.entity.CanIdsStatusInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanIdsStatusInfoLogRepository extends JpaRepository<CanIdsStatusInfoLog, Integer>  {
}
