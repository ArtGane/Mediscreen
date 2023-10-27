package com.mediscreen.front.feign;

import com.mediscreen.front.dto.AssessmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "assessment", url = "${url.assessment}")
public interface AssessmentFeign {

    @GetMapping()
    AssessmentDto getAssessmentByPatientId(@RequestParam("patId") Long patientId);
}
