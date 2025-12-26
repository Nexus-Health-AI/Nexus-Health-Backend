package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.AdmissionDischargeRequest;
import com.uciap.medicrea.dto.AdmissionRequest;
import com.uciap.medicrea.dto.AdmissionResponse;
import com.uciap.medicrea.dto.AdmissionTransferRequest;
import com.uciap.medicrea.service.AdmissionService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admissions")
public class AdmissionController {

    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @PostMapping
    public AdmissionResponse admitPatient(@RequestBody AdmissionRequest request) {
        return admissionService.admitPatient(request);
    }

    @GetMapping
    public List<AdmissionResponse> getAllAdmissions() {
        return admissionService.getAllAdmissions();
    }

    @GetMapping("/patient/{patientId}")
    public List<AdmissionResponse> getAdmissionsForPatient(@PathVariable String patientId) {
        return admissionService.getAdmissionsForPatient(patientId);
    }

    @PutMapping("/{admissionId}/transfer")
    public AdmissionResponse transferAdmission(@PathVariable String admissionId,
                                               @RequestBody AdmissionTransferRequest request) {
        return admissionService.transferAdmission(admissionId, request);
    }

    @PutMapping("/{admissionId}/discharge")
    public AdmissionResponse dischargeAdmission(@PathVariable String admissionId,
                                                @RequestBody AdmissionDischargeRequest request) {
        return admissionService.dischargeAdmission(admissionId, request);
    }
}
