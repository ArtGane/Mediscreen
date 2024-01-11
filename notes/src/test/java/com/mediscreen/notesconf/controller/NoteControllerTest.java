package com.mediscreen.notesconf.controller;

import com.mediscreen.notesconf.model.Note;
import com.mediscreen.notesconf.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoteController.class)
class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Test
    public void testCreateOrUpdateNote() throws Exception {
        Note note = new Note();
        when(noteService.createOrUpdateNote(any())).thenReturn(note);

        mockMvc.perform(post("/patHistory/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("param1", "value1")
                        .param("param2", "value2"))
                .andExpect(status().isOk());

        verify(noteService, times(1)).createOrUpdateNote(any());
    }

    @Test
    public void testCreateOrUpdateNoteByPatient() throws Exception {
        Note note = new Note();
        note.setId("12");
        note.setPatId("1L");
        note.setE("someValue");
        note.setDate();

        when(noteService.createOrUpdateNoteByPatId(argThat(n -> n.getId().equals("12")), eq("1L"))).thenReturn(note);

        mockMvc.perform(post("/patHistory/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"12\",\"patId\":\"1L\",\"e\":\"someValue\",\"date\":\"2024-01-11T00:00:00\"}"))
                .andExpect(status().isOk());

        verify(noteService, times(1)).createOrUpdateNoteByPatId(argThat(n -> n.getId().equals("12")), eq("1L"));
    }

    @Test
    public void testGetNoteById() throws Exception {
        String noteId = "123";
        Note note = new Note();
        when(noteService.getNoteById(noteId)).thenReturn(note);
        mockMvc.perform(get("/patHistory/note")
                        .param("id", noteId))
                .andExpect(status().isOk());
        verify(noteService, times(1)).getNoteById(noteId);
    }

    @Test
    public void testGetAllPatientNotes() throws Exception {
        String patId = "456";
        List<Note> notes = Arrays.asList(new Note(), new Note());
        when(noteService.getAllNotes(patId)).thenReturn(notes);

        mockMvc.perform(get("/patHistory/patient/all")
                        .param("patId", patId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        verify(noteService, times(1)).getAllNotes(patId);
    }

    @Test
    public void testDeleteNote() throws Exception {
        String noteId = "789";
        mockMvc.perform(delete("/patHistory/delete")
                        .param("id", noteId))
                .andExpect(status().isOk());
        verify(noteService, times(1)).deleteNote(noteId);
    }
}