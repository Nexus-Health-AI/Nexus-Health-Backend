package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.BedRequest;
import com.uciap.medicrea.dto.BedResponse;
import com.uciap.medicrea.dto.StatusUpdateRequest;
import com.uciap.medicrea.service.BedService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/beds")
public class BedController {

    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    @PostMapping
    public BedResponse createBed(@RequestBody BedRequest request) {
        return bedService.createBed(request);
    }

    @GetMapping
    public List<BedResponse> getBeds() {
        return bedService.getAllBeds();
    }

    @GetMapping("/ward/{wardId}")
    public List<BedResponse> getBedsForWard(@PathVariable String wardId) {
        return bedService.getBedsForWard(wardId);
    }

    @PutMapping("/{bedId}/status")
    public BedResponse updateStatus(@PathVariable String bedId, @RequestBody StatusUpdateRequest request) {
        return bedService.updateBedStatus(bedId, request.getStatus());
    }
}
