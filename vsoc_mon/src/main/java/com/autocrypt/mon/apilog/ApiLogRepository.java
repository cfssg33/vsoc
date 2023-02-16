package com.autocrypt.mon.apilog;

import com.autocrypt.mon.apilog.entity.ApiLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ApiLogRepository extends JpaRepository<ApiLog, Long> {
    List<ApiLog> findAllByUrlAndRequestAndCreatedTimeBetween(String url, String request, LocalDateTime loginTime, LocalDateTime currentTime);
    List<ApiLog> findAllByOrderByCreatedTimeDesc();
}
