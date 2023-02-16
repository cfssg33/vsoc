package com.autocrypt.can;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VsocCanApplication {

    public static void main(String[] args) {
        SpringApplication.run(VsocCanApplication.class, args);
    }
}
