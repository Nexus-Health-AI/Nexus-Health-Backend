package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.EmergencyCaseRequest;
import com.uciap.medicrea.dto.EmergencyCaseResponse;
import com.uciap.medicrea.dto.StatusUpdateRequest;
import com.uciap.medicrea.service.EmergencyCaseService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/emergency")
public class EmergencyController {

    private final EmergencyCaseService emergencyCaseService;

    public EmergencyController(EmergencyCaseService emergencyCaseService) {
        this.emergencyCaseService = emergencyCaseService;
    }

    @PostMapping("/cases")
    public EmergencyCaseResponse createCase(@RequestBody EmergencyCaseRequest request) {
        return emergencyCaseService.createCase(request);
    }

    @GetMapping("/cases")
    public List<EmergencyCaseResponse> getCases() {
        return emergencyCaseService.getAllCases();
    }

    @PutMapping("/cases/{id}/status")
    public EmergencyCaseResponse updateStatus(@PathVariable String id, @RequestBody StatusUpdateRequest request) {
        return emergencyCaseService.updateStatus(id, request.getStatus());
    }
}
