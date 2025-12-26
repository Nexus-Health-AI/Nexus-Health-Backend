package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.NurseRequest;
import com.uciap.medicrea.dto.NurseResponse;
import com.uciap.medicrea.model.Nurse;
import com.uciap.medicrea.repository.NurseRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class NurseService {

    private final NurseRepository nurseRepository;

    public NurseService(NurseRepository nurseRepository) {
        this.nurseRepository = nurseRepository;
    }

    public NurseResponse createNurse(NurseRequest request) {
        Nurse nurse = new Nurse();
        nurse.setFullName(request.getFullName());
        nurse.setWard(request.getWard());
        return toResponse(nurseRepository.save(nurse));
    }

    public List<NurseResponse> getAllNurses() {
        return nurseRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    private NurseResponse toResponse(Nurse nurse) {
        NurseResponse response = new NurseResponse();
        response.setId(nurse.getId());
        response.setFullName(nurse.getFullName());
        response.setWard(nurse.getWard());
        return response;
    }
}
