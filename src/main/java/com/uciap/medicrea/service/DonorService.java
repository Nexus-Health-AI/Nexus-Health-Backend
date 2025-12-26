package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.DonorRequest;
import com.uciap.medicrea.dto.DonorResponse;
import com.uciap.medicrea.model.Donor;
import com.uciap.medicrea.repository.DonorRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public DonorResponse createDonor(DonorRequest request) {
        Donor donor = new Donor();
        donor.setFullName(request.getFullName());
        donor.setBloodGroup(request.getBloodGroup());
        donor.setContact(request.getContact());
        donor.setLastDonationDate(request.getLastDonationDate());
        donor.setEligibilityStatus(request.getEligibilityStatus());
        donor.setNotes(request.getNotes());
        return toResponse(donorRepository.save(donor));
    }

    public List<DonorResponse> getAllDonors() {
        return donorRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public Donor getDonorEntity(String donorId) {
        return donorRepository.findById(donorId)
            .orElseThrow(() -> new RuntimeException("Donor not found"));
    }

    private DonorResponse toResponse(Donor donor) {
        DonorResponse response = new DonorResponse();
        response.setId(donor.getId());
        response.setFullName(donor.getFullName());
        response.setBloodGroup(donor.getBloodGroup());
        response.setContact(donor.getContact());
        response.setLastDonationDate(donor.getLastDonationDate());
        response.setEligibilityStatus(donor.getEligibilityStatus());
        response.setNotes(donor.getNotes());
        return response;
    }
}
