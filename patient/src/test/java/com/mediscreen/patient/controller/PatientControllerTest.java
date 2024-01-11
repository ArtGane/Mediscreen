package com.mediscreen.patient.controller;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    public void testGetAllPatients() throws Exception {
        Patient patient1 = new Patient();
        patient1.setFamily("Doe");
        patient1.setGiven("John");
        patient1.setDob(LocalDate.of(1990, 1, 1));
        patient1.setSex("Male");

        Patient patient2 = new Patient();
        patient2.setFamily("Smith");
        patient2.setGiven("Jane");
        patient2.setDob(LocalDate.of(1985, 5, 15));
        patient2.setSex("Female");

        List<Patient> patients = Arrays.asList(patient1, patient2);
        when(patientService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/patient/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    public void testDeletePatient() throws Exception {
        Long patientId = 1L;
        mockMvc.perform(delete("/patient/delete/{id}", patientId))
                .andExpect(status().isNoContent());
        verify(patientService, times(1)).deletePatient(patientId);
    }

    @Test
    public void testSavePatientUri() throws Exception {
        Patient patient = new Patient();
        patient.setFamily("Doe");
        patient.setGiven("John");
        patient.setDob(LocalDate.of(1990, 1, 1));
        patient.setSex("Male");        when(patientService.createOrUpdatePatient(any())).thenReturn(patient);
        mockMvc.perform(post("/patient/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("param1", "value1")
                        .param("param2", "value2"))
                .andExpect(status().is3xxRedirection());
        verify(patientService, times(1)).createOrUpdatePatient(any());
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setFamily("Doe");
        patient.setGiven("John");
        patient.setDob(LocalDate.of(1990, 1, 1));
        patient.setSex("Male");
        when(patientService.getPatientById(patientId)).thenReturn(patient);

        mockMvc.perform(get("/patient/edit/{id}", patientId))
                .andExpect(status().isOk());

        verify(patientService, times(1)).getPatientById(patientId);
    }

    @Test
    public void testSavePatient() throws Exception {
        Patient patient = new Patient();
        patient.setFamily("Doe");
        patient.setGiven("John");
        patient.setDob(LocalDate.of(1990, 1, 1));
        patient.setSex("Male");
        when(patientService.createOrUpdatePatient(any())).thenReturn(patient);
        mockMvc.perform(post("/patient/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
        verify(patientService, times(1)).createOrUpdatePatient(any());
    }

    @Test
    public void testGetPatientByLastnameAndFirstname() throws Exception {
        String lastname = "Doe";
        String firstname = "John";
        Patient patient = new Patient();
        patient.setFamily("Doe");
        patient.setGiven("John");
        patient.setDob(LocalDate.of(1990, 1, 1));
        patient.setSex("Male");

        when(patientService.getPatientByCompleteName(lastname, firstname)).thenReturn(patient);
        mockMvc.perform(get("/patient/search")
                        .param("lastname", lastname)
                        .param("firstname", firstname))
                .andExpect(status().isOk());
        verify(patientService, times(1)).getPatientByCompleteName(lastname, firstname);
    }

    @Test
    public void testRedirectToPatient() throws Exception {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setFamily("Doe");
        patient.setGiven("John");
        patient.setDob(LocalDate.of(1990, 1, 1));
        patient.setSex("Male");
        when(patientService.getPatientById(patientId)).thenReturn(patient);
        mockMvc.perform(post("/patient/redirect")
                        .param("id", String.valueOf(patientId)))
                .andExpect(status().isOk());
        verify(patientService, times(1)).getPatientById(patientId);
    }

    @Test
    public void testGetPatientById() throws Exception {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setFamily("Doe");
        patient.setGiven("John");
        patient.setDob(LocalDate.of(1990, 1, 1));
        patient.setSex("Male");
        when(patientService.getPatientById(patientId)).thenReturn(patient);

        mockMvc.perform(get("/patient/{id}", patientId))
                .andExpect(status().isOk());

        verify(patientService, times(1)).getPatientById(patientId);
    }

    @Test
    public void testGetPatientsOver30() throws Exception {
        mockMvc.perform(get("/patient/filter"))
                .andExpect(status().isOk());

        verify(patientService, times(1)).getPatientsByAgeOverAgeRequired(30);
    }

    @Test
    public void testGetPatientsByGender() throws Exception {
        String gender = "Male";
        mockMvc.perform(get("/patient/filterByGender")
                        .param("sex", gender))
                .andExpect(status().isOk());

        verify(patientService, times(1)).getOneGenderListOfPatients(gender);
    }
}