package com.mediscreen.front.controller;

import com.mediscreen.front.dto.AssessmentDto;
import com.mediscreen.front.dto.NoteDto;
import com.mediscreen.front.dto.PatientDto;
import com.mediscreen.front.feign.AssessmentFeign;
import com.mediscreen.front.feign.NoteFeign;
import com.mediscreen.front.feign.PatientFeign;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RequestMapping("/patient")
@Controller
public class PatientController {

    private final PatientFeign patientFeign;
    private final NoteFeign noteFeign;
    private final AssessmentFeign assessmentFeign;

    public PatientController(PatientFeign patientFeign, NoteFeign noteFeign, AssessmentFeign assessmentFeign) {
        this.patientFeign = patientFeign;
        this.noteFeign = noteFeign;
        this.assessmentFeign = assessmentFeign;
    }

    @GetMapping("/all")
    public String getAllPatients(Model model) {
        List<PatientDto> patientsDto = patientFeign.getAllPatients();
        model.addAttribute("patients", patientsDto);
        return "patient/patients";
    }

    @GetMapping("/show")
    public String showCreateForm(Model model, PatientDto patientDto) {
        model.addAttribute("patient", patientDto);
        return "patient/createPatient";
    }

    @GetMapping("/delete/{id}")
    public RedirectView deletePatient(@PathVariable Long id) {
        patientFeign.deletePatient(id);
        return new RedirectView("patient/patients");
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        PatientDto patientDto = patientFeign.getPatientById(id);
        model.addAttribute("patient", patientDto);
        return "patient/updatePatient";
    }

    @PostMapping("/add")
    public RedirectView savePatientUri(@ModelAttribute PatientDto patientDto) {
        patientFeign.savePatientUri(patientDto);
        return new RedirectView("patient/patients");
    }

    @PostMapping("/save")
    public String savePatient(@ModelAttribute("patient") PatientDto patientDto) {
        patientFeign.savePatient(patientDto);
        return "redirect:/patient/all";
    }

    @PostMapping("/redirect")
    public String redirectToPatient(@RequestParam("id") Long id, Model model) {
        PatientDto patientDto = patientFeign.getPatientById(id);
        List<NoteDto> noteDtos = noteFeign.getAllPatientNotes(id.toString());
        AssessmentDto assessmentDto = assessmentFeign.getAssessmentByPatientId(id);

        model.addAttribute("patient", patientDto);
        model.addAttribute("notes", noteDtos);
        model.addAttribute("assessment", assessmentDto);

        return "patient/patient";
    }

    @GetMapping("/{id}")
    public String getPatientById(@PathVariable("id") Long id, Model model) {
        PatientDto patientDto = patientFeign.getPatientById(id);
        List<NoteDto> noteDtos = noteFeign.getAllPatientNotes(id.toString());
        model.addAttribute("patient", patientDto);
        model.addAttribute("notes", noteDtos);
        return "patient/patient";

    }

    @GetMapping("/filter")
    public String getPatientsOver30(Model model) {
        List<PatientDto> filteredPatients = patientFeign.getPatientsOver30();
        model.addAttribute("patients", filteredPatients);
        return "patient/patients";
    }

    @GetMapping("/filterByGender")
    public String getPatientsByGender(@RequestParam String sex, Model model) {
        List<PatientDto> patientDtos = patientFeign.getPatientsByGender(sex);
        model.addAttribute("patients", patientDtos);
        return "patient/patients";
    }

}
