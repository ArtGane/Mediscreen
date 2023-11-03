package com.mediscreen.front.controller;

import com.mediscreen.front.dto.AssessmentDto;
import com.mediscreen.front.dto.NoteDto;
import com.mediscreen.front.dto.PatientDto;
import com.mediscreen.front.feign.AssessmentFeign;
import com.mediscreen.front.feign.NoteFeign;
import com.mediscreen.front.feign.PatientFeign;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientFeign patientFeign;
    @MockBean
    private NoteFeign noteFeign;
    @MockBean
    private AssessmentFeign assessmentFeign;

    @Test
    void testGetAllPatients() throws Exception {
        List<PatientDto> patients = Collections.singletonList(new PatientDto());
        when(patientFeign.getAllPatients()).thenReturn(patients);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient/patients"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("patients"));

        verify(patientFeign, times(1)).getAllPatients();
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient/createPatient"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    void testDeletePatient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/delete/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("patient/patients"));

        verify(patientFeign, times(1)).deletePatient(1L);
    }

    @Test
    void testShowUpdateForm() throws Exception {
        PatientDto patientDto = new PatientDto();
        when(patientFeign.getPatientById(1L)).thenReturn(patientDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/edit/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient/updatePatient"))
                .andExpect(model().attributeExists("patient"));

        verify(patientFeign, times(1)).getPatientById(1L);
    }

    @Test
    void testSavePatientUri() throws Exception {
        RedirectView expectedRedirect = new RedirectView("/patient/patients");
        when(patientFeign.savePatientUri(any(PatientDto.class))).thenReturn(expectedRedirect);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/add")
                        .param("attribute1", "value1")
                        .param("attribute2", "value2"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        verify(patientFeign, times(1)).savePatientUri(any(PatientDto.class));
    }

    @Test
    void testSavePatient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/patient/save")
                        .param("attribute1", "value1")
                        .param("attribute2", "value2"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient/all"));

        verify(patientFeign, times(1)).savePatient(any(PatientDto.class));
    }

    @Test
    void testRedirectToPatient() throws Exception {
        Long patientId = 1L;
        PatientDto patientDto = new PatientDto();
        List<NoteDto> noteDtos = Collections.singletonList(new NoteDto());
        AssessmentDto assessmentDto = new AssessmentDto(1L, 0, "None");
        when(patientFeign.getPatientById(patientId)).thenReturn(patientDto);
        when(noteFeign.getAllPatientNotes(patientId.toString())).thenReturn(noteDtos);
        when(assessmentFeign.getAssessmentByPatientId(patientId)).thenReturn(assessmentDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/redirect")
                        .param("id", patientId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient/patient"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("notes"));

        verify(patientFeign, times(1)).getPatientById(patientId);
        verify(noteFeign, times(1)).getAllPatientNotes(patientId.toString());
    }

    @Test
    void testGetPatientsOver30() throws Exception {
        List<PatientDto> filteredPatients = Collections.singletonList(new PatientDto());
        when(patientFeign.getPatientsOver30()).thenReturn(filteredPatients);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/filter"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient/patients"))
                .andExpect(model().attributeExists("patients"));

        verify(patientFeign, times(1)).getPatientsOver30();
    }

    @Test
    void testGetPatientsByGender() throws Exception {
        String gender = "M";
        List<PatientDto> patientDtos = Collections.singletonList(new PatientDto());
        when(patientFeign.getPatientsByGender(gender)).thenReturn(patientDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/filterByGender")
                        .param("sex", gender))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient/patients"))
                .andExpect(model().attributeExists("patients"));

        verify(patientFeign, times(1)).getPatientsByGender(gender);
    }
}