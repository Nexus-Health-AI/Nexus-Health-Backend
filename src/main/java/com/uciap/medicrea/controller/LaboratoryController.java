package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.LabResultRequest;
import com.uciap.medicrea.dto.LabResultResponse;
import com.uciap.medicrea.dto.LabTestRequest;
import com.uciap.medicrea.dto.LabTestResponse;
import com.uciap.medicrea.service.LabResultService;
import com.uciap.medicrea.service.LabTestService;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class LaboratoryController {

    private final LabTestService labTestService;
    private final LabResultService labResultService;

    public LaboratoryController(LabTestService labTestService, LabResultService labResultService) {
        this.labTestService = labTestService;
        this.labResultService = labResultService;
    }

    @PostMapping("/lab-tests")
    public LabTestResponse createLabTest(@RequestBody LabTestRequest request) {
        return labTestService.createLabTest(request);
    }

    @GetMapping("/lab-tests")
    public List<LabTestResponse> getLabTests() {
        return labTestService.getAllTests();
    }

    @PostMapping("/lab-results")
    public LabResultResponse createLabResult(@RequestBody LabResultRequest request) {
        return labResultService.createLabResult(request);
    }

    @GetMapping("/lab-results/patient/{patientId}")
    public List<LabResultResponse> getLabResults(@PathVariable String patientId) {
        return labResultService.getLabResultsForPatient(patientId);
    }
}
