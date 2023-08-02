package org.mediscreen.front.controller;

import com.mediscreen.patient.exception.UnknowPatient;
import org.mediscreen.front.dto.PatientDto;
import org.mediscreen.front.feign.PatientFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RequestMapping("/patient")
@Controller
public class PatientController {

    @Autowired
    PatientFeign patientFeign;

    @GetMapping("/all")
    public String getAllPatients(Model model) {
        List<PatientDto> patientsDto = patientFeign.getAllPatients();
        model.addAttribute("patients", patientsDto);
        return "/patient/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientFeign.deletePatient(id);
        return "redirect:/patient/patients";
    }

    @GetMapping("/show")
    public String showCreateForm(Model model, PatientDto patientDto) {
        model.addAttribute("patient", patientDto);
        return "/patient/createPatient";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        PatientDto patientDto = patientFeign.getPatientById(id);
        model.addAttribute("patient", patientDto);
        return "/patient/updatePatient";
    }

    @PostMapping("/add")
    @RequestMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView savePatientUri(@ModelAttribute PatientDto patientDto) {
        patientFeign.savePatientUri(patientDto);
        return new RedirectView("/patient/patients");
    }

    @PostMapping("/save")
    public String savePatient(@ModelAttribute("patient") PatientDto patientDto) {
        patientFeign.savePatient(patientDto);
        return "redirect:/patient/patients";
    }

    @GetMapping("/")
    public String getPatientByLastnameAndFirstname(@RequestParam String lastname, @RequestParam String firstname, Model model) throws UnknowPatient {
        PatientDto patientDto = patientFeign.getPatientByLastnameAndFirstname(lastname, firstname);
        model.addAttribute("patient", patientDto);
        return "/patient/patient";
    }

    @PostMapping("/")
    public String redirectToPatient(@RequestParam("id") Long id, Model model) {
        PatientDto patientDto = patientFeign.getPatientById(id);
        model.addAttribute("patient", patientDto);
        return "/patient/patient";
    }

    @GetMapping("/{id}")
    public String getPatientById(@RequestParam Long id, Model model) {
        PatientDto patientDto = patientFeign.getPatientById(id);
        model.addAttribute("patient", patientDto);
        return "/patient/patient";

    }

    @GetMapping("/filter")
    public String getPatientsOver30(Model model) {
        List<PatientDto> filteredPatients = patientFeign.getPatientsOver30();
        model.addAttribute("patients", filteredPatients);
        return "/patient/patients";
    }

    @GetMapping("/filterByGender")
    public String getPatientsByGender(@RequestParam String sex, Model model) {
        List<PatientDto> patientDtos = patientFeign.getPatientsByGender(sex);
        model.addAttribute("patients", patientDtos);
        return "/patient/patients";
    }

}
