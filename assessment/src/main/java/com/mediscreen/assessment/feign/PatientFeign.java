package com.mediscreen.assessment.feign;

import com.mediscreen.assessment.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@FeignClient(name = "patient", url = "${url.patient}")
public interface PatientFeign {

    @GetMapping("/all")
    List<PatientDto> getAllPatients();

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deletePatient(@PathVariable Long id);

    @PostMapping("/add")
    RedirectView savePatientUri(@ModelAttribute PatientDto newPatientDto);

    @GetMapping("/edit/{id}")
    PatientDto showUpdateForm(@PathVariable Long id);

    @PostMapping("/save")
    ResponseEntity<Void> savePatient(@ModelAttribute("patient") PatientDto newPatientDto);

    @PostMapping("/redirect")
    PatientDto redirectToPatient(@RequestParam("id") Long id);

    @GetMapping("/{id}")
    PatientDto getPatientById(@PathVariable Long id);

    @GetMapping("/filter")
    List<PatientDto> getPatientsOver30();

    @GetMapping("/filterByGender")
    List<PatientDto> getPatientsByGender(@RequestParam String sex);
}
