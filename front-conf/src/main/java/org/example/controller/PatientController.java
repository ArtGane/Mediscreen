package org.example.controller;

import com.mediscreen.patient.exception.UnknowPatient;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RequestMapping()
@Controller
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping("/patients")
    public String getAllPatients(Model model) {
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patients";
    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }

    @GetMapping("/patient/show")
    public String showCreateForm(Model model) {
        Patient newPatient = new Patient();
        model.addAttribute("patient", newPatient);
        return "createPatient";
    }

    @GetMapping("/patients/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "updatePatient";
    }

    @PostMapping("/patient/add")
    @RequestMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView savePatientUri(@ModelAttribute Patient newPatient) {
        patientService.createPatient(newPatient);
        return new RedirectView("/patients");
    }

    @PostMapping("/patient/save")
    public String savePatient(@ModelAttribute("patient") Patient newPatient) {
        patientService.createPatient(newPatient);
        return "redirect:/patients";
    }

    @GetMapping("/patients/patient")
    public String getPatientByLastnameAndFirstname(@RequestParam String lastname, @RequestParam String firstname, Model model) throws UnknowPatient, UnknowPatient {
        Patient patient = patientService.getPatientByCompleteName(lastname, firstname);
        model.addAttribute("patient", patient);
        return "patient";
    }

    @PostMapping("/patients/patient")
    public String redirectToPatient(@RequestParam("id") Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "patient";
    }

    @GetMapping("/patients/{id}")
    public String getPatientById(@RequestParam Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "patient";

    }

    @GetMapping("/patients/filter")
    public String getPatientsOver30(Model model) {
        List<Patient> filteredPatients = patientService.getPatientsByAgeOverAgeRequired(30);
        model.addAttribute("patients", filteredPatients);
        return "patients";
    }

    @GetMapping("/patients/filterByGender")
    public String getPatientsByGender(@RequestParam String sex, Model model) {
        List<Patient> patients = patientService.getOneGenderListOfPatients(sex);
        model.addAttribute("patients", patients);
        return "patients";
    }

}
