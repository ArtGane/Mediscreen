package com.mediscreen.assessment.controller;

import com.mediscreen.assessment.feign.NoteFeign;
import com.mediscreen.assessment.feign.PatientFeign;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/assessment")
public class AssessmentController {

    private final PatientFeign patientFeign;
    private final NoteFeign noteFeign;

    public AssessmentController(NoteFeign noteFeign, PatientFeign patientFeign) {
        this.noteFeign = noteFeign;
        this.patientFeign = patientFeign;
    }
}
