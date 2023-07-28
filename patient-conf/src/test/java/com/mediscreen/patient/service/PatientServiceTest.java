package com.mediscreen.patient.service;

import com.mediscreen.patient.exception.UnknowPatient;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @InjectMocks
    PatientService patientService;

    @Mock
    PatientRepository patientRepository;

    private List<Patient> patients;

    @BeforeEach
    void init() {
        patients = new ArrayList<>();
        Patient patientFemme = new Patient(1L, "Doe", "Jane", LocalDate.of(1989, 05, 8), "F");
        Patient patientHomme = new Patient(2L, "Doe", "John", LocalDate.of(1994, 12, 28), "H");

        patients.add(patientFemme);
        patients.add(patientHomme);

        when(patientRepository.findAll()).thenReturn(patients);
        when(patientRepository.getReferenceById(1L)).thenReturn(patientFemme);
    }

    @Test
    void TestGetAllPatients() {
        List<Patient> result = patientService.getAllPatients();
        assertEquals(patients.size(), result.size());
        assertTrue(result.containsAll(patients));
    }

    @Test
    void TestGetPatientById() {
        Long patientId = 1L;
        Patient result = patientService.getPatientById(patientId);
        assertTrue(result.getId().equals(patientId));
    }

    @Test
    void TestGetPatientByCompleteName() throws UnknowPatient {
        String lastname = "Doe";
        String firstname = "Jane";
        Patient result = patientService.getPatientByCompleteName(lastname, firstname);
        assertNotNull(result);
        assertEquals(lastname, result.getFamily());
        assertEquals(firstname, result.getGiven());
    }

    @Test
    void TestGetPatientsByLastname() throws UnknowPatient {
        String lastname = "Doe";
        List<Patient> result = patientService.getPatientsByLastname(lastname);
        assertNotNull(result);
        assertTrue(result.stream().allMatch(p -> p.getFamily().equals(lastname)));
    }

    @Test
    void TestGetPatientsByAgeOverAgeRequired() {
        int ageRequired = 30;
        List<Patient> result = patientService.getPatientsByAgeOverAgeRequired(ageRequired);
        assertTrue(result.stream().allMatch(p -> p.getAge() > ageRequired));
    }

    @Test
    void TestGetOneGenderOverAgeRequired() {
        String gender = "F";
        List<Patient> result = patientService.getOneGenderListOfPatients(gender);
        assertTrue(result.stream().allMatch(p -> p.getSex().equalsIgnoreCase(gender)));
    }
}
