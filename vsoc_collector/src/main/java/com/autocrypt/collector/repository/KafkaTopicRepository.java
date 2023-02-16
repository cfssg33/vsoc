package com.autocrypt.collector.repository;

import com.autocrypt.collector.repository.entity.KafkaTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface KafkaTopicRepository extends JpaRepository<KafkaTopic, String> {
}
