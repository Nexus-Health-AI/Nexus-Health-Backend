package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.MedicalAnalysisResponse;
import com.uciap.medicrea.service.MedicalAnalysisService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class MedicalAnalysisController {

    private final MedicalAnalysisService medicalAnalysisService;

    public MedicalAnalysisController(MedicalAnalysisService medicalAnalysisService) {
        this.medicalAnalysisService = medicalAnalysisService;
    }

    @PostMapping(value = "/analysis", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MedicalAnalysisResponse analyzeReport(
        @RequestParam("file") MultipartFile file,
        @RequestParam(value = "reportType", required = false) String reportType,
        @RequestParam(value = "notes", required = false) String notes,
        @RequestParam(value = "bodyPart", required = false) String bodyPart
    ) {
        return medicalAnalysisService.analyze(file, reportType, notes, bodyPart);
    }
}
