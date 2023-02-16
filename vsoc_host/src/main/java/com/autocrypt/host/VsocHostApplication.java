package com.autocrypt.host;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VsocHostApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(VsocHostApplication.class, args);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
