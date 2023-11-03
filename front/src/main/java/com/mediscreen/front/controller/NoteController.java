package com.mediscreen.front.controller;

import com.mediscreen.front.dto.AssessmentDto;
import com.mediscreen.front.dto.NoteDto;
import com.mediscreen.front.dto.PatientDto;
import com.mediscreen.front.feign.AssessmentFeign;
import com.mediscreen.front.feign.NoteFeign;
import com.mediscreen.front.feign.PatientFeign;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/patHistory")
public class NoteController {

    private final NoteFeign noteFeign;
    private final PatientFeign patientFeign;
    private final AssessmentFeign assessmentFeign;

    public NoteController(NoteFeign noteFeign, PatientFeign patientFeign, AssessmentFeign assessmentFeign) {
        this.noteFeign = noteFeign;
        this.patientFeign = patientFeign;
        this.assessmentFeign = assessmentFeign;
    }

    @PostMapping("/save")
    public String createOrUpdateNote(@ModelAttribute NoteDto noteDto, Model model) {
        noteFeign.createNoteByPatId(noteDto);
        PatientDto patientDto = patientFeign.getPatientById(Long.parseLong(noteDto.getPatId()));
        AssessmentDto assessmentDto = assessmentFeign.getAssessmentByPatientId(Long.parseLong(noteDto.getPatId()));
        return "redirect:/patient/all";
    }

    @GetMapping("/show")
    public String showCreateNoteForm(@RequestParam("patId") String patId, Model model) {
        PatientDto patientDto = patientFeign.getPatientById(Long.parseLong(patId));
        model.addAttribute("patientName", patientDto.getFamily());
        model.addAttribute("patId", patientDto.getId());
        model.addAttribute("note", new NoteDto());
        return "notes/createNote";
    }

    @GetMapping("/note")
    public String getNoteById(@RequestParam("id") String id, Model model) {
        NoteDto noteDto = noteFeign.getNoteById(id);
        model.addAttribute("note", noteDto);
        return "notes/note";
    }

    @GetMapping("/patient")
    public String getAllPatientNotes(@RequestParam("patId") String patId, Model model) {
        List<NoteDto> notes = noteFeign.getAllPatientNotes(patId);
        PatientDto patientDto = patientFeign.getPatientById(Long.valueOf(patId));
        model.addAttribute("patient", patientDto);
        model.addAttribute("notes", notes);
        return "patient/patient";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("id") String id, @RequestParam("patId") String patId) {
        noteFeign.deleteNote(id);
        return "redirect:/patient/all";
    }

    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam String id, Model model) {
        NoteDto noteDto = noteFeign.getNoteById(id);
        PatientDto patientDto = patientFeign.getPatientById(Long.parseLong(noteDto.getPatId()));
        model.addAttribute("id", noteDto.getId());
        model.addAttribute("patId", patientDto.getId());
        model.addAttribute("note", noteDto);
        model.addAttribute("patientName", patientDto.getFamily());
        return "notes/createNote.html";
    }
}
