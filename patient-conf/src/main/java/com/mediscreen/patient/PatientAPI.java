package com.mediscreen.patient;

import com.mediscreen.patient.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PatientAPI {

	private Logger log = LoggerFactory.getLogger(PatientAPI.class);

	public static void main(String[] args) {
		SpringApplication.run(PatientAPI.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Patient patient = restTemplate.getForObject(
					"http://localhost:8081/patient/add", Patient.class);
			log.error(patient.toString());
		};
	}
}