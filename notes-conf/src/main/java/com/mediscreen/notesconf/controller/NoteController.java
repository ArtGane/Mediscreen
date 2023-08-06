package com.mediscreen.notesconf.controller;

import com.mediscreen.notesconf.model.Note;
import com.mediscreen.notesconf.service.NoteService;
import org.bson.types.ObjectId;
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

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable ObjectId id) {
        return noteService.getNoteById(id);
    }

    @GetMapping("/{id}/all")
    public List<Note> getAllPatientNotes(@PathVariable Long id) {
        return noteService.getAllNotes(id);
    }

    @GetMapping("/delete/{id}")
    public void deleteNote(@PathVariable ObjectId id) {
        noteService.deleteNote(id);
    }
}
