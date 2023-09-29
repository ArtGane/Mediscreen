package com.mediscreen.assessment.feign;

import com.mediscreen.assessment.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient", url = "localhost:8081")
public interface PatientFeign {

    @GetMapping("/patient/{id}")
    PatientDto getPatientById(@PathVariable Long id);
}
