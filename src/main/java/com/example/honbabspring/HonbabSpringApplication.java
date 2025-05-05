package com.example.honbabspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class HonbabSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(HonbabSpringApplication.class, args);
    }

}
