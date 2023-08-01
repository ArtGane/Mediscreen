package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PatientDto(String family, String given, LocalDate dob, String sex, String address, String phone) {
}
