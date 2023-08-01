package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

public class FrontAPI {

    private static final Logger log = LoggerFactory.getLogger(FrontAPI.class);
    public static void main(String[] args) {
        SpringApplication.run(FrontAPI.class, args);
    }
}