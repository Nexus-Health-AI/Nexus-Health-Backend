package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.InsurancePolicyRequest;
import com.uciap.medicrea.dto.InsurancePolicyResponse;
import com.uciap.medicrea.model.InsurancePolicy;
import com.uciap.medicrea.repository.InsurancePolicyRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class InsurancePolicyService {

    private final InsurancePolicyRepository insurancePolicyRepository;
    private final PatientService patientService;
    private final InsuranceProviderService insuranceProviderService;

    public InsurancePolicyService(InsurancePolicyRepository insurancePolicyRepository,
                                  PatientService patientService,
                                  InsuranceProviderService insuranceProviderService) {
        this.insurancePolicyRepository = insurancePolicyRepository;
        this.patientService = patientService;
        this.insuranceProviderService = insuranceProviderService;
    }

    public InsurancePolicyResponse createPolicy(InsurancePolicyRequest request) {
        patientService.getPatientEntity(request.getPatientId());
        insuranceProviderService.getProviderEntity(request.getProviderId());
        InsurancePolicy policy = new InsurancePolicy();
        policy.setPatientId(request.getPatientId());
        policy.setProviderId(request.getProviderId());
        policy.setPolicyNumber(request.getPolicyNumber());
        policy.setCoverageDetails(request.getCoverageDetails());
        policy.setValidFrom(request.getValidFrom());
        policy.setValidTo(request.getValidTo());
        policy.setStatus(request.getStatus() == null || request.getStatus().isBlank() ? "ACTIVE" : request.getStatus());
        return toResponse(insurancePolicyRepository.save(policy));
    }

    public List<InsurancePolicyResponse> getAllPolicies() {
        return insurancePolicyRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<InsurancePolicyResponse> getPoliciesForPatient(String patientId) {
        patientService.getPatientEntity(patientId);
        return insurancePolicyRepository.findByPatientId(patientId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public InsurancePolicy getPolicyEntity(String policyId) {
        return insurancePolicyRepository.findById(policyId)
            .orElseThrow(() -> new RuntimeException("Insurance policy not found"));
    }

    private InsurancePolicyResponse toResponse(InsurancePolicy policy) {
        InsurancePolicyResponse response = new InsurancePolicyResponse();
        response.setId(policy.getId());
        response.setPatientId(policy.getPatientId());
        response.setProviderId(policy.getProviderId());
        response.setPolicyNumber(policy.getPolicyNumber());
        response.setCoverageDetails(policy.getCoverageDetails());
        response.setValidFrom(policy.getValidFrom());
        response.setValidTo(policy.getValidTo());
        response.setStatus(policy.getStatus());
        return response;
    }
}
