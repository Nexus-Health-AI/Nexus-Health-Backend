package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.InsuranceProviderRequest;
import com.uciap.medicrea.dto.InsuranceProviderResponse;
import com.uciap.medicrea.model.InsuranceProvider;
import com.uciap.medicrea.repository.InsuranceProviderRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class InsuranceProviderService {

    private final InsuranceProviderRepository insuranceProviderRepository;

    public InsuranceProviderService(InsuranceProviderRepository insuranceProviderRepository) {
        this.insuranceProviderRepository = insuranceProviderRepository;
    }

    public InsuranceProviderResponse createProvider(InsuranceProviderRequest request) {
        InsuranceProvider provider = new InsuranceProvider();
        provider.setName(request.getName());
        provider.setContact(request.getContact());
        provider.setEmail(request.getEmail());
        provider.setAddress(request.getAddress());
        return toResponse(insuranceProviderRepository.save(provider));
    }

    public List<InsuranceProviderResponse> getAllProviders() {
        return insuranceProviderRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public InsuranceProvider getProviderEntity(String providerId) {
        return insuranceProviderRepository.findById(providerId)
            .orElseThrow(() -> new RuntimeException("Insurance provider not found"));
    }

    private InsuranceProviderResponse toResponse(InsuranceProvider provider) {
        InsuranceProviderResponse response = new InsuranceProviderResponse();
        response.setId(provider.getId());
        response.setName(provider.getName());
        response.setContact(provider.getContact());
        response.setEmail(provider.getEmail());
        response.setAddress(provider.getAddress());
        return response;
    }
}
