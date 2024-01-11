package com.mediscreen.patient.service;

import com.mediscreen.patient.exception.UnknowPatient;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private Patient patientFemme;
    private Patient patientHomme;


    @BeforeEach
    void init() {
        patients = new ArrayList<>();
        patientFemme = new Patient(1L, "Doe", "Jane", LocalDate.of(1989, 05, 8), "F");
        patientHomme = new Patient(2L, "Doe", "John", LocalDate.of(1994, 12, 28), "H");

        patients.add(patientFemme);
        patients.add(patientHomme);

    }

    @Test
    void testCreateOrUpdatePatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patientFemme);

        Patient result = patientService.createOrUpdatePatient(patientFemme);

        assertNotNull(result);
        assertEquals(patientFemme.getId(), result.getId());
        assertEquals(patientFemme.getFamily(), result.getFamily());
        assertEquals(patientFemme.getGiven(), result.getGiven());
        assertEquals(patientFemme.getDob(), result.getDob());
        assertEquals(patientFemme.getSex(), result.getSex());

        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void testDeletePatient() {
        Long patientId = 2L;

        when(patientRepository.getReferenceById(patientId)).thenReturn(patientHomme);

        patientService.deletePatient(patientId);

        verify(patientRepository, times(1)).getReferenceById(patientId);
        verify(patientRepository, times(1)).delete(patientHomme);
    }

    @Test
    void TestGetAllPatients() {
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientService.getAllPatients();
        assertEquals(patients.size(), result.size());
        assertTrue(result.containsAll(patients));
    }

    @Test
    void TestGetPatientById() {
        when(patientRepository.findById(1L)).thenReturn(Optional.ofNullable(patientFemme));

        Patient result = patientService.getPatientById(1L);
        assertTrue(result.getId().equals(1L));
    }

    @Test
    void TestGetPatientByCompleteName() throws UnknowPatient {
        String lastname = "Doe";
        String firstname = "Jane";
        when(patientRepository.findPatientByFamily(lastname)).thenReturn(patients);

        Patient result = patientService.getPatientByCompleteName(lastname, firstname);
        assertNotNull(result);
        assertEquals(lastname, result.getFamily());
        assertEquals(firstname, result.getGiven());
    }

    @Test
    void TestGetPatientsByLastname() throws UnknowPatient {
        String lastname = "Doe";
        when(patientRepository.findPatientByFamily(lastname)).thenReturn(patients);

        List<Patient> result = patientService.getPatientsByLastname(lastname);
        assertTrue(result.size() == 2);
        assertTrue(result.stream().allMatch(p -> p.getFamily().equals(lastname)));
    }

    @Test
    void TestGetPatientsByAgeOverAgeRequired() {
        int ageRequired = 30;
        when(patientRepository.findAll()).thenReturn(patients);
        List<Patient> result = patientService.getPatientsByAgeOverAgeRequired(ageRequired);
        assertTrue(result.stream().allMatch(p -> p.getAge() > ageRequired));
    }

    @Test
    void TestGetOneGenderOverAgeRequired() {
        String gender = "F";
        when(patientRepository.findAll()).thenReturn(patients);
        List<Patient> result = patientService.getOneGenderListOfPatients(gender);
        assertTrue(result.stream().allMatch(p -> p.getSex().equalsIgnoreCase(gender)));
    }
}
