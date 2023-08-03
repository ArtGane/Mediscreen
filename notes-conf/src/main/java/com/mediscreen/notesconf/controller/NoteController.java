package com.mediscreen.notesconf.controller;

import com.mediscreen.notesconf.model.Note;
import com.mediscreen.notesconf.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patHistory")
public class NoteController {

    @Autowired
    NoteService noteService;

    @PostMapping("/save")
    public Note createOrUpdateNote(@RequestBody Note note) {
        return noteService.createOrUpdateNote(note);
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @GetMapping("/{id}/all")
    public List<Note> getAllPatientNotes(@PathVariable Long id) {
        return noteService.getAllPatientNotes(id);
    }

    @GetMapping("/delete/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
    }
}
