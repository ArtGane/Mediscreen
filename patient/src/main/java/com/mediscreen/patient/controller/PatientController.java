package com.mediscreen.patient.controller;

import com.mediscreen.patient.exception.UnknowPatient;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @GetMapping("/all")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView savePatientUri(@ModelAttribute Patient newPatient) {
        patientService.createOrUpdatePatient(newPatient);
        return new RedirectView("/all");
    }

    @GetMapping("/edit/{id}")
    public Patient showUpdateForm(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Patient savePatient(@RequestBody Patient newPatient) {
        return patientService.createOrUpdatePatient(newPatient);
    }

    @GetMapping("/search")
    public Patient getPatientByLastnameAndFirstname(@RequestParam String lastname, @RequestParam String firstname) throws UnknowPatient {
        return patientService.getPatientByCompleteName(lastname, firstname);
    }

    @PostMapping("/redirect")
    public Patient redirectToPatient(@RequestParam("id") Long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/filter")
    public List<Patient> getPatientsOver30() {
        return patientService.getPatientsByAgeOverAgeRequired(30);
    }

    @GetMapping("/filterByGender")
    public List<Patient> getPatientsByGender(@RequestParam String sex) {
        return patientService.getOneGenderListOfPatients(sex);
    }
}

