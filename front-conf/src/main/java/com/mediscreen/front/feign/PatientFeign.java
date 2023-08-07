package com.mediscreen.front.feign;

import com.mediscreen.front.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@FeignClient(name = "patient-conf", url = "localhost:8083")
public interface PatientFeign {

    @GetMapping("/patient")
    List<PatientDto> getAllPatients();

    @GetMapping("/patient/delete/{id}")
    ResponseEntity<Void> deletePatient(@PathVariable Long id);

    @PostMapping("/patient/add")
    @RequestMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    RedirectView savePatientUri(@ModelAttribute PatientDto newPatientDto);

    @GetMapping("/patient/edit/{id}")
    PatientDto showUpdateForm(@PathVariable Long id);

    @PostMapping("/patient/save")
    ResponseEntity<Void> savePatient(@ModelAttribute("patient") PatientDto newPatientDto);

    @GetMapping("/patient/")
    PatientDto getPatientByLastnameAndFirstname(@RequestParam String lastname, @RequestParam String firstname);

    @PostMapping("/patient/patient")
    PatientDto redirectToPatient(@RequestParam("id") Long id);

    @GetMapping("/patient/{id}")
    PatientDto getPatientById(@PathVariable Long id);

    @GetMapping("/patient/filter")
    List<PatientDto> getPatientsOver30();

    @GetMapping("/patient/filterByGender")
    List<PatientDto> getPatientsByGender(@RequestParam String sex);
}
