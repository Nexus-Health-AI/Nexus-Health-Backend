package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.MedicalReportResponse;
import com.uciap.medicrea.service.MedicalReportService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class MedicalReportController {

    private final MedicalReportService medicalReportService;

    public MedicalReportController(MedicalReportService medicalReportService) {
        this.medicalReportService = medicalReportService;
    }

    @PostMapping("/reports/generate/patient/{patientId}")
    public MedicalReportResponse generate(@PathVariable String patientId) {
        return medicalReportService.generatePatientReport(patientId);
    }
    @GetMapping("/reports/generate/patient/{patientId}")
    public List<MedicalReportResponse> generatePatient(@PathVariable String patientId) {
        return medicalReportService.getReportByPatientId(patientId);
    }

    @GetMapping(value = "/reports/patient/{patientId}/html", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getPatientReportHtml(@PathVariable String patientId) {
        String html = medicalReportService.generatePatientReportHtml(patientId);
        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_HTML)
            .body(html);
    }
}
