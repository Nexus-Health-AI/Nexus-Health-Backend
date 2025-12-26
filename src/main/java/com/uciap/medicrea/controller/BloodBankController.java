package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.BloodUnitRequest;
import com.uciap.medicrea.dto.BloodUnitResponse;
import com.uciap.medicrea.dto.DonorRequest;
import com.uciap.medicrea.dto.DonorResponse;
import com.uciap.medicrea.service.BloodUnitService;
import com.uciap.medicrea.service.DonorService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/blood-bank")
public class BloodBankController {

    private final DonorService donorService;
    private final BloodUnitService bloodUnitService;

    public BloodBankController(DonorService donorService, BloodUnitService bloodUnitService) {
        this.donorService = donorService;
        this.bloodUnitService = bloodUnitService;
    }

    @PostMapping("/donors")
    public DonorResponse createDonor(@RequestBody DonorRequest request) {
        return donorService.createDonor(request);
    }

    @GetMapping("/donors")
    public List<DonorResponse> getDonors() {
        return donorService.getAllDonors();
    }

    @PostMapping("/units")
    public BloodUnitResponse createUnit(@RequestBody BloodUnitRequest request) {
        return bloodUnitService.createBloodUnit(request);
    }

    @GetMapping("/units")
    public List<BloodUnitResponse> getUnits() {
        return bloodUnitService.getAllUnits();
    }

    @GetMapping("/units/available")
    public List<BloodUnitResponse> getAvailableUnits() {
        return bloodUnitService.getAvailableUnits();
    }
}
