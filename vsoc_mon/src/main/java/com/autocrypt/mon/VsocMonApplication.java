package com.autocrypt.mon;

import com.autocrypt.mon.apilog.ApiLogRepository;
import com.autocrypt.mon.apilog.entity.ApiLog;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@EnableJpaAuditing
@EnableAspectJAutoProxy
@SpringBootApplication
public class VsocMonApplication {

    @Autowired ApiLogRepository apiLogRepository;
    public static void main(String[] args) {
        SpringApplication.run(VsocMonApplication.class, args);
    }


    @Bean
    @Transactional
    InitializingBean initializingBean() {
        ApiLog apiLog = ApiLog.builder()
                .url("/")
                .createdTime(LocalDateTime.now())
                .level("SUCCESS")
                .method("NONE")
                .sourceIp("localhost")
                .request("Service Start")
                .response("Service Start")
                .build();

        apiLogRepository.save(apiLog);
        return null;
    }
}
