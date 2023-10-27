package com.mediscreen.assessment.controller;

import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.feign.NoteFeign;
import com.mediscreen.assessment.feign.PatientFeign;
import com.mediscreen.assessment.model.Assessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.mediscreen.assessment.service.AssessmentService;

@RestController
@RequestMapping("/assess")
public class AssessmentController {

    private final NoteFeign noteFeign;
    private final PatientFeign patientFeign;

    public AssessmentController(NoteFeign noteFeign, PatientFeign patientFeign) {
        this.noteFeign = noteFeign;
        this.patientFeign = patientFeign;
    }
    @Autowired
    private AssessmentService assessmentService;

    @GetMapping()
    public Assessment getPatientAssessment(@RequestParam Long patId) {
        Assessment assessment = assessmentService.getAssessmentByPatientId(patId);
        return assessment;
    }

    @PostMapping(path = "/id", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String getPatientAssessmentFromUri(@RequestParam Long patId) {
        Assessment assessment = assessmentService.getAssessmentByPatientId(patId);
        PatientDto patientDto = patientFeign.getPatientById(patId);
        String result = "Patient: " + patientDto.getFamily() + " (age " + assessment.getAge() + ") diabetes assessment is: " + assessment.getRiskLevel();
        return result;
    }

}
