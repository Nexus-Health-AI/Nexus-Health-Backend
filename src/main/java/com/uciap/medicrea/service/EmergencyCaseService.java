package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.EmergencyCaseRequest;
import com.uciap.medicrea.dto.EmergencyCaseResponse;
import com.uciap.medicrea.model.EmergencyCase;
import com.uciap.medicrea.repository.EmergencyCaseRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class EmergencyCaseService {

    private final EmergencyCaseRepository emergencyCaseRepository;
    private final PatientService patientService;

    public EmergencyCaseService(EmergencyCaseRepository emergencyCaseRepository,
                                PatientService patientService) {
        this.emergencyCaseRepository = emergencyCaseRepository;
        this.patientService = patientService;
    }

    public EmergencyCaseResponse createCase(EmergencyCaseRequest request) {
        if (request.getPatientId() != null && !request.getPatientId().isBlank()) {
            patientService.getPatientEntity(request.getPatientId());
        }
        EmergencyCase emergencyCase = new EmergencyCase();
        emergencyCase.setPatientId(request.getPatientId());
        emergencyCase.setArrivalTime(request.getArrivalTime() != null ? request.getArrivalTime() : LocalDateTime.now());
        emergencyCase.setTriageLevel(request.getTriageLevel());
        emergencyCase.setChiefComplaint(request.getChiefComplaint());
        emergencyCase.setStatus(request.getStatus() == null || request.getStatus().isBlank() ? "TRIAGED" : request.getStatus());
        emergencyCase.setAmbulanceNumber(request.getAmbulanceNumber());
        emergencyCase.setBroughtBy(request.getBroughtBy());
        emergencyCase.setContact(request.getContact());
        emergencyCase.setNotes(request.getNotes());
        return toResponse(emergencyCaseRepository.save(emergencyCase));
    }

    public EmergencyCaseResponse updateStatus(String id, String status) {
        EmergencyCase emergencyCase = emergencyCaseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Emergency case not found"));
        emergencyCase.setStatus(status);
        return toResponse(emergencyCaseRepository.save(emergencyCase));
    }

    public List<EmergencyCaseResponse> getAllCases() {
        return emergencyCaseRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private EmergencyCaseResponse toResponse(EmergencyCase emergencyCase) {
        EmergencyCaseResponse response = new EmergencyCaseResponse();
        response.setId(emergencyCase.getId());
        response.setPatientId(emergencyCase.getPatientId());
        response.setArrivalTime(emergencyCase.getArrivalTime());
        response.setTriageLevel(emergencyCase.getTriageLevel());
        response.setChiefComplaint(emergencyCase.getChiefComplaint());
        response.setStatus(emergencyCase.getStatus());
        response.setAmbulanceNumber(emergencyCase.getAmbulanceNumber());
        response.setBroughtBy(emergencyCase.getBroughtBy());
        response.setContact(emergencyCase.getContact());
        response.setNotes(emergencyCase.getNotes());
        return response;
    }
}
