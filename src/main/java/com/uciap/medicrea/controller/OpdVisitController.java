package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.OpdVisitRequest;
import com.uciap.medicrea.dto.OpdVisitResponse;
import com.uciap.medicrea.dto.StatusUpdateRequest;
import com.uciap.medicrea.service.OpdVisitService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/opd")
public class OpdVisitController {

    private final OpdVisitService opdVisitService;

    public OpdVisitController(OpdVisitService opdVisitService) {
        this.opdVisitService = opdVisitService;
    }

    @PostMapping("/visits")
    public OpdVisitResponse createVisit(@RequestBody OpdVisitRequest request) {
        return opdVisitService.createVisit(request);
    }

    @GetMapping("/visits")
    public List<OpdVisitResponse> getAllVisits() {
        return opdVisitService.getAllVisits();
    }

    @GetMapping("/visits/patient/{patientId}")
    public List<OpdVisitResponse> getVisitsForPatient(@PathVariable String patientId) {
        return opdVisitService.getVisitsForPatient(patientId);
    }

    @PutMapping("/visits/{id}/status")
    public OpdVisitResponse updateStatus(@PathVariable String id, @RequestBody StatusUpdateRequest request) {
        return opdVisitService.updateStatus(id, request.getStatus());
    }
}
