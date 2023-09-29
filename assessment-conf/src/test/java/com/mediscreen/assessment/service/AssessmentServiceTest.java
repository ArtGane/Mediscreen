package com.mediscreen.assessment.service;

import com.mediscreen.assessment.dto.NoteDto;
import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.feign.NoteFeign;
import com.mediscreen.assessment.feign.PatientFeign;
import com.mediscreen.assessment.model.Assessment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssessmentServiceTest {

    private AssessmentService assessmentService;
    private PatientFeign proxyPatient;
    private NoteFeign proxyNotes;

    @BeforeEach
    void setUp() {
        proxyPatient = mock(PatientFeign.class);
        proxyNotes = mock(NoteFeign.class);
    }

    @Test
    void getAssessmentByPatientIdTest() {
        PatientDto patientDto = new PatientDto();
        patientDto.setAge(30);
        patientDto.setSex("M");

        List<NoteDto> noteDtoList = new ArrayList<>();

        when(proxyPatient.getPatientById(anyLong())).thenReturn(patientDto);
        when(proxyNotes.getAllPatientNotes(anyString())).thenReturn(noteDtoList);

        Assessment assessment = assessmentService.getAssessmentByPatientId(1L);

        assertNotNull(assessment);
    }
}