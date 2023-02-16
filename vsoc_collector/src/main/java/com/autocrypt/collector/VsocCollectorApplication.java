package com.autocrypt.collector;

import com.autocrypt.collector.service.KafkaService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VsocCollectorApplication {

    @Autowired
    public KafkaService kafkaService;

    public static void main(String[] args) {
        SpringApplication.run(VsocCollectorApplication.class, args);
    }

    @Bean
    InitializingBean initializingBean() {
        kafkaService.startConsumer();
        return null;
    }
}
