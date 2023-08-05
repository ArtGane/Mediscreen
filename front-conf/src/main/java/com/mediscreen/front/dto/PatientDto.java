package com.mediscreen.front.dto;


import org.springframework.cloud.openfeign.FeignClient;

import java.time.LocalDate;
@FeignClient(value = "PatientService",url="localhost:8081/patient")
public class PatientDto {

    private Long id;
    private String family;
    private String given;
    private LocalDate dob;
    private String sex;
    private String address;
    private String phone;

}
