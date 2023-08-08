package com.mediscreen.notesconf.controller;

import com.mediscreen.notesconf.model.Note;
import com.mediscreen.notesconf.service.NoteService;
import java.lang.String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patHistory")
public class NoteController {

    @Autowired
    NoteService noteService;

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Note createOrUpdateNote(@ModelAttribute Note note) {
        return noteService.createOrUpdateNote(note);
    }

    @PostMapping("/save")
    public Note createOrUpdateNoteByPatient(@ModelAttribute Note note, @RequestParam("patId") Long patId) {
        return noteService.createOrUpdateNoteByPatId(note, patId);
    }

    @GetMapping("/note")
    public Note getNoteById(@RequestParam("id") String id) {
        return noteService.getNoteById(id);
    }

    @GetMapping("/patient/all")
    public List<Note> getAllPatientNotes(@RequestParam String patId) {
        return noteService.getAllNotes(patId);
    }

    @GetMapping("/delete")
    public void deleteNote(@RequestParam String id) {
        noteService.deleteNote(id);
    }

}
