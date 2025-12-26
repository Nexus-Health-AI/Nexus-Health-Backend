package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.OpdVisitRequest;
import com.uciap.medicrea.dto.OpdVisitResponse;
import com.uciap.medicrea.model.OpdVisit;
import com.uciap.medicrea.repository.OpdVisitRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class OpdVisitService {

    private final OpdVisitRepository opdVisitRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public OpdVisitService(OpdVisitRepository opdVisitRepository,
                           PatientService patientService,
                           DoctorService doctorService) {
        this.opdVisitRepository = opdVisitRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public OpdVisitResponse createVisit(OpdVisitRequest request) {
        patientService.getPatientEntity(request.getPatientId());
        if (request.getDoctorId() != null && !request.getDoctorId().isBlank()) {
            doctorService.getDoctorEntity(request.getDoctorId());
        }
        OpdVisit visit = new OpdVisit();
        applyRequest(visit, request);
        if (visit.getVisitDate() == null) {
            visit.setVisitDate(LocalDateTime.now());
        }
        if (visit.getStatus() == null || visit.getStatus().isBlank()) {
            visit.setStatus("OPEN");
        }
        return toResponse(opdVisitRepository.save(visit));
    }

    public OpdVisitResponse updateStatus(String id, String status) {
        OpdVisit visit = opdVisitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("OPD visit not found"));
        visit.setStatus(status);
        return toResponse(opdVisitRepository.save(visit));
    }

    public List<OpdVisitResponse> getAllVisits() {
        return opdVisitRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<OpdVisitResponse> getVisitsForPatient(String patientId) {
        patientService.getPatientEntity(patientId);
        return opdVisitRepository.findByPatientId(patientId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private void applyRequest(OpdVisit visit, OpdVisitRequest request) {
        visit.setPatientId(request.getPatientId());
        visit.setDoctorId(request.getDoctorId());
        visit.setDepartment(request.getDepartment());
        visit.setVisitDate(request.getVisitDate());
        visit.setTriageLevel(request.getTriageLevel());
        visit.setChiefComplaint(request.getChiefComplaint());
        visit.setBloodPressure(request.getBloodPressure());
        visit.setTemperature(request.getTemperature());
        visit.setPulse(request.getPulse());
        visit.setRespirationRate(request.getRespirationRate());
        visit.setWeight(request.getWeight());
        visit.setNotes(request.getNotes());
        visit.setStatus(request.getStatus());
    }

    private OpdVisitResponse toResponse(OpdVisit visit) {
        OpdVisitResponse response = new OpdVisitResponse();
        response.setId(visit.getId());
        response.setPatientId(visit.getPatientId());
        response.setDoctorId(visit.getDoctorId());
        response.setDepartment(visit.getDepartment());
        response.setVisitDate(visit.getVisitDate());
        response.setTriageLevel(visit.getTriageLevel());
        response.setChiefComplaint(visit.getChiefComplaint());
        response.setBloodPressure(visit.getBloodPressure());
        response.setTemperature(visit.getTemperature());
        response.setPulse(visit.getPulse());
        response.setRespirationRate(visit.getRespirationRate());
        response.setWeight(visit.getWeight());
        response.setNotes(visit.getNotes());
        response.setStatus(visit.getStatus());
        return response;
    }
}
