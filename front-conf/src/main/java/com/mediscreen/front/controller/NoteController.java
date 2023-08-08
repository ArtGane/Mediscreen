package com.mediscreen.front.controller;

import com.mediscreen.front.dto.NoteDto;
import com.mediscreen.front.dto.PatientDto;
import com.mediscreen.front.feign.NoteFeign;
import com.mediscreen.front.feign.PatientFeign;
import org.bson.types.ObjectId;
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

    public NoteController(NoteFeign noteFeign, PatientFeign patientFeign) {
        this.noteFeign = noteFeign;
        this.patientFeign = patientFeign;
    }

    @PostMapping("/save")
    public String createOrUpdateNote(@ModelAttribute NoteDto noteDto, @RequestParam("patId") String patId, Model model) {
        noteFeign.createNoteByPatId(Map.of("patId", patId, "e", noteDto.getE()));
        PatientDto patientDto = patientFeign.getPatientById(Long.parseLong(patId));
        return String.format("redirect:/patient/%s", patId);
    }

    @GetMapping("/show")
    public String showCreateNoteForm(@RequestParam("patId") String patId, Model model) {
        PatientDto patientDto = patientFeign.getPatientById(Long.parseLong(patId));
        model.addAttribute("patientName", patientDto.getFamily());
        model.addAttribute("patId", patId);
        model.addAttribute("note", new NoteDto());
        return "/notes/createNote";
    }

    @GetMapping("/note")
    public String getNoteById(@RequestParam("id") String id, Model model) {
        NoteDto noteDto = noteFeign.getNoteById(id);
        model.addAttribute("note", noteDto);
        return "/notes/note";
    }

    @GetMapping("/patient/all")
    public String getAllPatientNotes(@RequestParam("patId") String patId, Model model) {
        List<NoteDto> notes = noteFeign.getAllPatientNotes(patId);
        model.addAttribute("notes", notes);
        return "/patient/patient";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("id") String id) {
        noteFeign.deleteNote(id);
        return "redirect:/patient/patient";
    }
}
