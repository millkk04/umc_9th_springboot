package com.example.umc_9th_final_5th.global;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.umc_9th_final_5th")
@EnableJpaRepositories(basePackages = "com.example.umc_9th_final_5th")
@EntityScan(basePackages = "com.example.umc_9th_final_5th")
public class Umc9thFinal5thApplication {

    public static void main(String[] args) {
        SpringApplication.run(Umc9thFinal5thApplication.class, args);
    }

}
