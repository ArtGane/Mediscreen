package service;

import com.mediscreen.assessment.dto.NoteDto;
import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.feign.NoteFeign;
import com.mediscreen.assessment.feign.PatientFeign;
import com.mediscreen.assessment.model.Assessment;
import com.mediscreen.assessment.service.AssessmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssessmentServiceTest {

    @InjectMocks
    private AssessmentService assessmentService;

    @Mock
    private PatientFeign patientFeign;

    @Mock
    private NoteFeign noteFeign;

    @Test
    void getAssessmentByPatientIdTest() {
        PatientDto patientDto = new PatientDto();
        patientDto.setAge(30);
        patientDto.setSex("M");
        List<NoteDto> noteDtoList = new ArrayList<>();
        when(patientFeign.getPatientById(anyLong())).thenReturn(patientDto);
        when(noteFeign.getAllPatientNotes(anyString())).thenReturn(noteDtoList);
        Assessment assessment = assessmentService.getAssessmentByPatientId(1L);
        assertNotNull(assessment);
    }
}