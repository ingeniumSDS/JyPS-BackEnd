package com.ingenium.jyps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JyPsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JyPsApplication.class, args);
    }

}
