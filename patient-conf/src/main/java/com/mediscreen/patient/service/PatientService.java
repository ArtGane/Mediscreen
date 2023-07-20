package com.mediscreen.patient.service;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public void createPatient(Patient patient) {
        patientRepository.save(patient);
    }

    public void deletePatient(Patient patient) {
        patientRepository.delete(patient);
    }

    public void updatePatient() {
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.getReferenceById(id);
    }

    public Patient getPatientByCompleteName(String lastname, String firstname) {
        Patient patient = getPatientsByLastname(lastname).stream()
                .filter(p -> p.getFirstname().equals(firstname))
                .findAny().orElseThrow();

        return patient;
    }

    public List<Patient> getPatientsByLastname(String lastname) {
        return patientRepository.findPatientByLastname(lastname);
    }

    public List<Patient> getPatientsByAgeOverAgeRequired(int ageRequired) {
        List<Patient> patients = new ArrayList<>();

        for (Patient patient : getAllPatients()) {
            if (patient.getAge() > ageRequired) {
                patients.add(patient);
            }
        }

        return patients;
    }

    public List<Patient> getOneGenderOverAgeRequired(String gender, int age) {
        List<Patient> genderList = new ArrayList<>();

        for (Patient patient : getPatientsByAgeOverAgeRequired(age)) {
            if (patient.getGender().equals(gender)) {
                genderList.add(patient);
            }
        }

        return genderList;
    }
}
