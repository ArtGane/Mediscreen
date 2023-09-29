package com.mediscreen.front.feign;

import com.mediscreen.front.dto.NoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "notes", url = "localhost:8082")
public interface NoteFeign {

    @PostMapping("/patHistory/add")
    void createNote(@ModelAttribute NoteDto noteDto);

    @PostMapping(value = "/patHistory/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void createNoteByPatId(Map<String, ?> data);

    @GetMapping("/patHistory/edit")
    NoteDto showUpdateForm(@RequestParam String id);

    @GetMapping("/patHistory/note")
    NoteDto getNoteById(@RequestParam("id") String id);

    @GetMapping("/patHistory/patient/all")
    List<NoteDto> getAllPatientNotes(@RequestParam("patId") String patId);

    @GetMapping("/patHistory/delete")
    void deleteNote(@RequestParam("id") String id);

}
