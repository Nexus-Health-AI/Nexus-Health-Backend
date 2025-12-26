package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.WardRequest;
import com.uciap.medicrea.dto.WardResponse;
import com.uciap.medicrea.model.Ward;
import com.uciap.medicrea.repository.WardRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WardService {

    private final WardRepository wardRepository;

    public WardService(WardRepository wardRepository) {
        this.wardRepository = wardRepository;
    }

    public WardResponse createWard(WardRequest request) {
        Ward ward = new Ward();
        ward.setName(request.getName());
        ward.setDepartment(request.getDepartment());
        ward.setType(request.getType());
        ward.setCapacity(request.getCapacity());
        ward.setNotes(request.getNotes());
        return toResponse(wardRepository.save(ward));
    }

    public List<WardResponse> getAllWards() {
        return wardRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private WardResponse toResponse(Ward ward) {
        WardResponse response = new WardResponse();
        response.setId(ward.getId());
        response.setName(ward.getName());
        response.setDepartment(ward.getDepartment());
        response.setType(ward.getType());
        response.setCapacity(ward.getCapacity());
        response.setNotes(ward.getNotes());
        return response;
    }
}
