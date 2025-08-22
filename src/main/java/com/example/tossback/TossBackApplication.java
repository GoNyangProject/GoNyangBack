package com.example.tossback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TossBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(TossBackApplication.class, args);
    }

}
