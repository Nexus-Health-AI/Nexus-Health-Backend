package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.BloodUnitRequest;
import com.uciap.medicrea.dto.BloodUnitResponse;
import com.uciap.medicrea.model.BloodUnit;
import com.uciap.medicrea.repository.BloodUnitRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BloodUnitService {

    private final BloodUnitRepository bloodUnitRepository;
    private final DonorService donorService;

    public BloodUnitService(BloodUnitRepository bloodUnitRepository,
                            DonorService donorService) {
        this.bloodUnitRepository = bloodUnitRepository;
        this.donorService = donorService;
    }

    public BloodUnitResponse createBloodUnit(BloodUnitRequest request) {
        if (request.getDonorId() != null && !request.getDonorId().isBlank()) {
            donorService.getDonorEntity(request.getDonorId());
        }
        BloodUnit unit = new BloodUnit();
        unit.setDonorId(request.getDonorId());
        unit.setBloodGroup(request.getBloodGroup());
        unit.setCollectedDate(request.getCollectedDate());
        unit.setExpiryDate(request.getExpiryDate());
        unit.setStatus(request.getStatus() == null || request.getStatus().isBlank() ? "AVAILABLE" : request.getStatus());
        unit.setQuantityMl(request.getQuantityMl());
        unit.setNotes(request.getNotes());
        return toResponse(bloodUnitRepository.save(unit));
    }

    public List<BloodUnitResponse> getAllUnits() {
        return bloodUnitRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<BloodUnitResponse> getAvailableUnits() {
        return bloodUnitRepository.findByStatus("AVAILABLE").stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private BloodUnitResponse toResponse(BloodUnit unit) {
        BloodUnitResponse response = new BloodUnitResponse();
        response.setId(unit.getId());
        response.setDonorId(unit.getDonorId());
        response.setBloodGroup(unit.getBloodGroup());
        response.setCollectedDate(unit.getCollectedDate());
        response.setExpiryDate(unit.getExpiryDate());
        response.setStatus(unit.getStatus());
        response.setQuantityMl(unit.getQuantityMl());
        response.setNotes(unit.getNotes());
        return response;
    }
}
