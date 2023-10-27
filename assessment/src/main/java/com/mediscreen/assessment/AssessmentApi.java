package com.mediscreen.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen.assessment.feign")
public class AssessmentApi {

    public static void main(String[] args) {
        SpringApplication.run(AssessmentApi.class, args);
    }

}
