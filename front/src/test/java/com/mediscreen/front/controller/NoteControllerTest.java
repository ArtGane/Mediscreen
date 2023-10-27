package com.mediscreen.front.controller;

import com.mediscreen.front.dto.NoteDto;
import com.mediscreen.front.dto.PatientDto;
import com.mediscreen.front.feign.AssessmentFeign;
import com.mediscreen.front.feign.NoteFeign;
import com.mediscreen.front.feign.PatientFeign;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientFeign patientFeign;
    @MockBean
    private NoteFeign noteFeign;
    @MockBean
    private AssessmentFeign assessmentFeign;

    @Test
    void testCreateOrUpdateNote() throws Exception {
        String patId = "1";
        NoteDto noteDto = new NoteDto();
        noteDto.setE("Some note content");

        when(patientFeign.getPatientById(Long.parseLong(patId))).thenReturn(new PatientDto());
        mockMvc.perform(MockMvcRequestBuilders.post("/patHistory/save")
                        .param("patId", patId)
                        .param("e", noteDto.getE()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patient/all"));

        verify(patientFeign, times(1)).getPatientById(Long.parseLong(patId));
        verify(noteFeign, times(1)).createNoteByPatId(Map.of("patId", patId, "e", noteDto.getE()));
    }

    @Test
    void testShowCreateNoteForm() throws Exception {
        String patId = "1";
        PatientDto patientDto = new PatientDto();
        patientDto.setFamily("Doe");
        when(patientFeign.getPatientById(Long.parseLong(patId))).thenReturn(patientDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/patHistory/show")
                        .param("patId", patId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("notes/createNote"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("patientName"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("note"));

        verify(patientFeign, times(1)).getPatientById(Long.parseLong(patId));
    }

    @Test
    void testGetNoteById() throws Exception {
        String noteId = "1";
        NoteDto noteDto = new NoteDto();

        when(noteFeign.getNoteById(noteId)).thenReturn(noteDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/patHistory/note")
                        .param("id", noteId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("notes/note"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("note"));

        verify(noteFeign, times(1)).getNoteById(noteId);
    }

    @Test
    void testDeleteNote() throws Exception {
        String noteId = "1";
        String patId = "2";
        mockMvc.perform(MockMvcRequestBuilders.get("/patHistory/delete")
                        .param("id", noteId)
                        .param("patId", patId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patient/all"));
        verify(noteFeign, times(1)).deleteNote(noteId);
    }

    @Test
    void testShowUpdateForm() throws Exception {
        String noteId = "1";
        NoteDto noteDto = new NoteDto();
        noteDto.setPatId("2");
        PatientDto patientDto = new PatientDto();
        patientDto.setFamily("Doe");

        when(noteFeign.getNoteById(noteId)).thenReturn(noteDto);
        when(patientFeign.getPatientById(Long.parseLong(noteDto.getPatId()))).thenReturn(patientDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/patHistory/edit")
                        .param("id", noteId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("notes/createNote.html"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("note"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("patientName"));

        verify(noteFeign, times(1)).getNoteById(noteId);
        verify(patientFeign, times(1)).getPatientById(Long.parseLong(noteDto.getPatId()));
    }
}