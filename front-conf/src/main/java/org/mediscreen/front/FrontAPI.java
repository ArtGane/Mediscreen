package org.mediscreen.front;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("org.mediscreen.front.feign")
public class FrontAPI {

    public static void main(String[] args) {
        SpringApplication.run(FrontAPI.class, args);
    }

}