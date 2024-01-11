package com.mediscreen.assessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.feign.NoteFeign;
import com.mediscreen.assessment.feign.PatientFeign;
import com.mediscreen.assessment.model.Assessment;
import com.mediscreen.assessment.service.AssessmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(AssessmentController.class)
public class AssessmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteFeign noteFeign;

    @MockBean
    private PatientFeign patientFeign;

    @MockBean
    private AssessmentService assessmentService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetPatientAssessment() throws Exception {
        Assessment mockAssessment = new Assessment(1L, 30, "Borderline");
        when(assessmentService.getAssessmentByPatientId(anyLong())).thenReturn(mockAssessment);
        mockMvc.perform(get("/assess").param("patId", "1"))
                .andExpect(status().isOk());
        verify(assessmentService, times(1)).getAssessmentByPatientId(1L);
    }

    @Test
    public void testGetPatientAssessmentFromUri() throws Exception {
        Assessment mockAssessment = new Assessment(1L, 30, "Borderline");
        when(assessmentService.getAssessmentByPatientId(anyLong())).thenReturn(mockAssessment);
        PatientDto mockPatientDto = new PatientDto();
        mockPatientDto.setFamily("Doe");
        when(patientFeign.getPatientById(anyLong())).thenReturn(mockPatientDto);
        mockMvc.perform(post("/assess/id")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("patId", "1"))
                .andExpect(status().isOk());
        verify(assessmentService, times(1)).getAssessmentByPatientId(1L);
        verify(patientFeign, times(1)).getPatientById(1L);
    }
}