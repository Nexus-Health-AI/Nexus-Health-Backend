package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.InsuranceClaimRequest;
import com.uciap.medicrea.dto.InsuranceClaimResponse;
import com.uciap.medicrea.model.InsuranceClaim;
import com.uciap.medicrea.repository.InsuranceClaimRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class InsuranceClaimService {

    private final InsuranceClaimRepository insuranceClaimRepository;
    private final InsurancePolicyService insurancePolicyService;
    private final InvoiceService invoiceService;

    public InsuranceClaimService(InsuranceClaimRepository insuranceClaimRepository,
                                 InsurancePolicyService insurancePolicyService,
                                 InvoiceService invoiceService) {
        this.insuranceClaimRepository = insuranceClaimRepository;
        this.insurancePolicyService = insurancePolicyService;
        this.invoiceService = invoiceService;
    }

    public InsuranceClaimResponse createClaim(InsuranceClaimRequest request) {
        invoiceService.getInvoiceEntity(request.getInvoiceId());
        insurancePolicyService.getPolicyEntity(request.getPolicyId());
        InsuranceClaim claim = new InsuranceClaim();
        claim.setInvoiceId(request.getInvoiceId());
        claim.setPolicyId(request.getPolicyId());
        claim.setStatus(request.getStatus() == null || request.getStatus().isBlank() ? "SUBMITTED" : request.getStatus());
        claim.setSubmittedAt(request.getSubmittedAt() != null ? request.getSubmittedAt() : LocalDateTime.now());
        claim.setApprovedAmount(request.getApprovedAmount());
        claim.setNotes(request.getNotes());
        return toResponse(insuranceClaimRepository.save(claim));
    }

    public InsuranceClaimResponse updateStatus(String claimId, String status) {
        InsuranceClaim claim = insuranceClaimRepository.findById(claimId)
            .orElseThrow(() -> new RuntimeException("Insurance claim not found"));
        claim.setStatus(status);
        return toResponse(insuranceClaimRepository.save(claim));
    }

    public List<InsuranceClaimResponse> getAllClaims() {
        return insuranceClaimRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private InsuranceClaimResponse toResponse(InsuranceClaim claim) {
        InsuranceClaimResponse response = new InsuranceClaimResponse();
        response.setId(claim.getId());
        response.setInvoiceId(claim.getInvoiceId());
        response.setPolicyId(claim.getPolicyId());
        response.setStatus(claim.getStatus());
        response.setSubmittedAt(claim.getSubmittedAt());
        response.setApprovedAmount(claim.getApprovedAmount());
        response.setNotes(claim.getNotes());
        return response;
    }
}
