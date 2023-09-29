package com.mediscreen.assessment.feign;

import com.mediscreen.assessment.dto.NoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "notes", url = "localhost:8082")
public interface NoteFeign {

    @GetMapping("/patHistory/patient/all")
    List<NoteDto> getAllPatientNotes(@RequestParam("patId") String patId);
}
