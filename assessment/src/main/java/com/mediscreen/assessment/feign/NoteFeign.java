package com.mediscreen.assessment.feign;

import com.mediscreen.assessment.dto.NoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "notes", url = "${url.notes}")
public interface NoteFeign {

    @PostMapping("/add")
    void createNote(@ModelAttribute NoteDto noteDto);

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void createNoteByPatId(Map<String, ?> data);

    @GetMapping("/edit")
    NoteDto showUpdateForm(@RequestParam String id);

    @GetMapping("/note")
    NoteDto getNoteById(@RequestParam("id") String id);

    @GetMapping("/patient/all")
    List<NoteDto> getAllPatientNotes(@RequestParam("patId") String patId);

    @GetMapping("/delete")
    void deleteNote(@RequestParam("id") String id);

}
