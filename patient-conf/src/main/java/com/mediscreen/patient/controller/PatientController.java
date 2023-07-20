package com.mediscreen.patient.controller;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/dashboard")
@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping()
    public Patient getPatientById(@RequestParam Long id) {
        Patient patient = patientService.getPatientById(id);
        return patient;
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients(){
        List<Patient> patients = patientService.getAllPatients();
        return patients;
    }

    @GetMapping("/patients/{age}")
    public List<Patient> getAllPatientsOverAge(@RequestParam int age) {
        List<Patient> patients = patientService.getPatientsByAgeOverAgeRequired(age);
        return patients;
    }



}
