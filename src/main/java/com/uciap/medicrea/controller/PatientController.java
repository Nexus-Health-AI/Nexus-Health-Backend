package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.PatientRequest;
import com.uciap.medicrea.dto.PatientResponse;
import com.uciap.medicrea.service.PatientService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/patients")
    public PatientResponse createPatient(@RequestBody PatientRequest request) {
        return patientService.createPatient(request);
    }

    @PutMapping("/patients/{id}")
    public PatientResponse updatePatient(@PathVariable String id, @RequestBody PatientRequest request) {
        return patientService.updatePatient(id, request);
    }

    @GetMapping("/patients/{id}")
    public PatientResponse getPatient(@PathVariable String id) {
        return patientService.getPatient(id);
    }

    @GetMapping("/patients")
    public List<PatientResponse> getPatients() {
        return patientService.getAllPatients();
    }

    @DeleteMapping("/patients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable String id) {
        patientService.softDeletePatient(id);
    }
}
