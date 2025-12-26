package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.InsuranceClaimRequest;
import com.uciap.medicrea.dto.InsuranceClaimResponse;
import com.uciap.medicrea.dto.InsurancePolicyRequest;
import com.uciap.medicrea.dto.InsurancePolicyResponse;
import com.uciap.medicrea.dto.InsuranceProviderRequest;
import com.uciap.medicrea.dto.InsuranceProviderResponse;
import com.uciap.medicrea.dto.StatusUpdateRequest;
import com.uciap.medicrea.service.InsuranceClaimService;
import com.uciap.medicrea.service.InsurancePolicyService;
import com.uciap.medicrea.service.InsuranceProviderService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/insurance")
public class InsuranceController {

    private final InsuranceProviderService insuranceProviderService;
    private final InsurancePolicyService insurancePolicyService;
    private final InsuranceClaimService insuranceClaimService;

    public InsuranceController(InsuranceProviderService insuranceProviderService,
                               InsurancePolicyService insurancePolicyService,
                               InsuranceClaimService insuranceClaimService) {
        this.insuranceProviderService = insuranceProviderService;
        this.insurancePolicyService = insurancePolicyService;
        this.insuranceClaimService = insuranceClaimService;
    }

    @PostMapping("/providers")
    public InsuranceProviderResponse createProvider(@RequestBody InsuranceProviderRequest request) {
        return insuranceProviderService.createProvider(request);
    }

    @GetMapping("/providers")
    public List<InsuranceProviderResponse> getProviders() {
        return insuranceProviderService.getAllProviders();
    }

    @PostMapping("/policies")
    public InsurancePolicyResponse createPolicy(@RequestBody InsurancePolicyRequest request) {
        return insurancePolicyService.createPolicy(request);
    }

    @GetMapping("/policies")
    public List<InsurancePolicyResponse> getPolicies() {
        return insurancePolicyService.getAllPolicies();
    }

    @GetMapping("/policies/patient/{patientId}")
    public List<InsurancePolicyResponse> getPoliciesForPatient(@PathVariable String patientId) {
        return insurancePolicyService.getPoliciesForPatient(patientId);
    }

    @PostMapping("/claims")
    public InsuranceClaimResponse createClaim(@RequestBody InsuranceClaimRequest request) {
        return insuranceClaimService.createClaim(request);
    }

    @GetMapping("/claims")
    public List<InsuranceClaimResponse> getClaims() {
        return insuranceClaimService.getAllClaims();
    }

    @PutMapping("/claims/{claimId}/status")
    public InsuranceClaimResponse updateClaimStatus(@PathVariable String claimId,
                                                    @RequestBody StatusUpdateRequest request) {
        return insuranceClaimService.updateStatus(claimId, request.getStatus());
    }
}
