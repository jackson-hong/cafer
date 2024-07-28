package com.spring.cafer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CaferApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaferApplication.class, args);
    }

}
