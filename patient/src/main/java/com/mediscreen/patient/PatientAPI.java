package com.mediscreen.patient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatientAPI {

	private Logger log = LoggerFactory.getLogger(PatientAPI.class);

	public static void main(String[] args) {
		SpringApplication.run(PatientAPI.class, args);
	}

}
