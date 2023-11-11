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

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Note createOrUpdateNoteByPatient(@RequestBody Note note) {
        return noteService.createOrUpdateNoteByPatId(note, note.getPatId());
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

    @GetMapping("/edit")
    public Note showUpdateForm(@RequestParam("id") String id) {
        Note note = noteService.getNoteById(id);
        return noteService.createOrUpdateNote(note);
    }
}
