package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.BedRequest;
import com.uciap.medicrea.dto.BedResponse;
import com.uciap.medicrea.model.Bed;
import com.uciap.medicrea.repository.BedRepository;
import com.uciap.medicrea.repository.WardRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BedService {

    private final BedRepository bedRepository;
    private final WardRepository wardRepository;

    public BedService(BedRepository bedRepository, WardRepository wardRepository) {
        this.bedRepository = bedRepository;
        this.wardRepository = wardRepository;
    }

    public BedResponse createBed(BedRequest request) {
        wardRepository.findById(request.getWardId())
            .orElseThrow(() -> new RuntimeException("Ward not found"));
        Bed bed = new Bed();
        bed.setWardId(request.getWardId());
        bed.setBedNumber(request.getBedNumber());
        bed.setStatus(request.getStatus() == null || request.getStatus().isBlank()
            ? "AVAILABLE" : request.getStatus());
        bed.setNotes(request.getNotes());
        return toResponse(bedRepository.save(bed));
    }

    public List<BedResponse> getAllBeds() {
        return bedRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<BedResponse> getBedsForWard(String wardId) {
        return bedRepository.findByWardId(wardId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public BedResponse updateBedStatus(String bedId, String status) {
        Bed bed = bedRepository.findById(bedId)
            .orElseThrow(() -> new RuntimeException("Bed not found"));
        bed.setStatus(status);
        return toResponse(bedRepository.save(bed));
    }

    private BedResponse toResponse(Bed bed) {
        BedResponse response = new BedResponse();
        response.setId(bed.getId());
        response.setWardId(bed.getWardId());
        response.setBedNumber(bed.getBedNumber());
        response.setStatus(bed.getStatus());
        response.setNotes(bed.getNotes());
        return response;
    }
}
