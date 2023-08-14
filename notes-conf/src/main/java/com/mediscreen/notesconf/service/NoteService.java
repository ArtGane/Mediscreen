package com.mediscreen.notesconf.service;

import com.mediscreen.notesconf.model.Note;
import com.mediscreen.notesconf.repository.NoteRepository;
import java.lang.String;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);

    @Autowired
    private NoteRepository noteRepository;

    public Note createOrUpdateNote(Note note) {
        if (note == null) {
            logger.error("La note est null. Impossible de créer ou mettre à jour la note.");
            throw new IllegalArgumentException("La note ne peut pas être null.");
        }
        noteRepository.save(note);
        logger.info("La note du {} a été créée ou mise à jour", note.getDate());
        return note;
    }

    public Note createOrUpdateNoteByPatId(Note note, Long patId) {
        if (note == null) {
            logger.error("La note est null. Impossible de créer ou mettre à jour la note.");
            throw new IllegalArgumentException("La note ne peut pas être null.");
        }
        note.setPatId(patId.toString());

        if (note.getDate() == null) {
            note.setDate();
        }
        noteRepository.save(note);
        logger.info("La note du {} a été créée ou mise à jour", note.getDate());
        return note;
    }

    public void deleteNote(String id) {
        Note note = getNoteById(id);
        if (note != null) {
            noteRepository.delete(note);
            logger.info("La note a été supprimée : {}", note);
        } else {
            logger.error("Aucune note trouvée avec l'ID : {}", id);
            throw new IllegalArgumentException("Aucune note trouvée avec l'ID : " + id);
        }
    }

    public Note getNoteById(String id) {
        Note note = noteRepository.findNoteById(id);
        if (note == null) {
            logger.error("Aucune note trouvée avec l'ID : {}", id);
            throw new IllegalArgumentException("Aucune note trouvée avec l'ID : " + id);
        }
        return note;
    }

    public List<Note> getAllNotes(String patId) {
        List<Note> patientNotes = noteRepository.findAllByPatId(patId);
        if (patientNotes.isEmpty()) {
            logger.warn("Aucune note trouvée pour le patient avec l'ID : {}", patId);
        }
        return patientNotes;
    }

    public List<Note> getNotesFromKey(String keyword) {
        List<Note> notes = noteRepository.findAll();
        List<Note> newNotes = new ArrayList<>();

        for (Note note : notes) {
            String[] notesByWords = note.getE().split(" ");
            for (String word : notesByWords) {
                if (word.equalsIgnoreCase(keyword)) newNotes.add(note);
            }
        }

        return newNotes;
    }
}
