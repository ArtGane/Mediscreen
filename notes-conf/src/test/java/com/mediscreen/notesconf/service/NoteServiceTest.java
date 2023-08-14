package com.mediscreen.notesconf.service;

import com.mediscreen.notesconf.model.Note;
import com.mediscreen.notesconf.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    private Note note;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        note = new Note("1", "pat1", "Example note with keyword");
    }

    @Test
    void testGetNoteById() {
        String noteId = "1";

        when(noteRepository.findNoteById(noteId)).thenReturn(note);

        Note result = noteService.getNoteById(noteId);

        assertNotNull(result);
        assertEquals(note.getId(), result.getId());
        assertEquals(note.getPatId(), result.getPatId());
        assertEquals(note.getE(), result.getE());

        verify(noteRepository, times(1)).findNoteById(noteId);
    }

    @Test
    void testCreateOrUpdateNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Note result = noteService.createOrUpdateNote(note);

        assertNotNull(result);
        assertEquals(note.getId(), result.getId());
        assertEquals(note.getPatId(), result.getPatId());
        assertEquals(note.getE(), result.getE());

        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void testCreateOrUpdateNoteByPatId() {
        Long patId = 1L;
        note.setPatId(null);

        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Note result = noteService.createOrUpdateNoteByPatId(note, patId);

        assertNotNull(result);
        assertEquals(note.getId(), result.getId());
        assertEquals(patId.toString(), result.getPatId());
        assertEquals(note.getE(), result.getE());

        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void testDeleteNote() {
        String noteId = "1";

        when(noteRepository.findNoteById(noteId)).thenReturn(note);

        assertDoesNotThrow(() -> noteService.deleteNote(noteId));

        verify(noteRepository, times(1)).delete(note);
    }

    @Test
    void testGetAllNotes() {
        String patId = "pat1";
        List<Note> patientNotes = new ArrayList<>();
        patientNotes.add(note);

        when(noteRepository.findAllByPatId(patId)).thenReturn(patientNotes);

        List<Note> result = noteService.getAllNotes(patId);

        assertNotNull(result);
        assertEquals(patientNotes.size(), result.size());
        assertTrue(result.containsAll(patientNotes));
    }

    @Test
    void testGetNotesFromKey() {
        String keyword = "keyword";
        List<Note> notes = new ArrayList<>();
        notes.add(note);

        when(noteRepository.findAll()).thenReturn(notes);

        List<Note> result = noteService.getNotesFromKey(keyword);

        assertNotNull(result);
        assertEquals(notes.size(), result.size());
        assertTrue(result.containsAll(notes));
    }
}