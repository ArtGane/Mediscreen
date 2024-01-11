package com.mediscreen.front.feign;

import com.mediscreen.front.dto.NoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "notes", url = "${url.note}")
public interface NoteFeign {
    @PostMapping(value = "/save")
    ResponseEntity<Void> createNoteByPatId(@ModelAttribute("note") NoteDto noteDto);

    @GetMapping("/edit")
    NoteDto showUpdateForm(@RequestParam String id);

    @GetMapping("/note")
    NoteDto getNoteById(@RequestParam("id") String id);

    @GetMapping("/patient/all")
    List<NoteDto> getAllPatientNotes(@RequestParam("patId") String patId);

    @DeleteMapping("/delete")
    void deleteNote(@RequestParam("id") String id);

}
