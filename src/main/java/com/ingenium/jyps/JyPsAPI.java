package com.ingenium.jyps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JyPsAPI {

    public static void main(String[] args) {
        SpringApplication.run(JyPsAPI.class, args);
    }

}
