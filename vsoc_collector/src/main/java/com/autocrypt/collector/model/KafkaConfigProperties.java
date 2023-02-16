package com.autocrypt.collector.model;

import com.autocrypt.collector.repository.KafkaTopicRepository;
import com.autocrypt.collector.repository.entity.KafkaTopic;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Configuration
@RequiredArgsConstructor
@PropertySource(value="file:/opt/autocrypt/vsoc/conf/config.properties", ignoreResourceNotFound = true)
public class KafkaConfigProperties {

    @Value("${kafka_ip}")
    private String kakfa_ip;

    @Value("${kafka_port}")
    private int port;

    private final KafkaTopicRepository kafkaTopicRepository;

    @Transactional
    @PostConstruct
    void init(){
        // Resetting the configuration with the data from DB
        List<KafkaTopic> kafkaTopicList = Optional.ofNullable(kafkaTopicRepository.findAll())
                .orElseGet(() -> new ArrayList<>());

        if (kafkaTopicList.size() > 0 && kakfa_ip != null){

            kafkaTopicList.stream().forEach((k) -> {
                k.setIpAddress(kakfa_ip);
                k.setPort(port);
            });

            kafkaTopicRepository.saveAll(kafkaTopicList);
        }
    }
}
