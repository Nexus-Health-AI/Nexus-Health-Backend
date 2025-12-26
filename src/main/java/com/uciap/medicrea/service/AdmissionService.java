package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.AdmissionDischargeRequest;
import com.uciap.medicrea.dto.AdmissionRequest;
import com.uciap.medicrea.dto.AdmissionResponse;
import com.uciap.medicrea.dto.AdmissionTransferRequest;
import com.uciap.medicrea.model.Admission;
import com.uciap.medicrea.model.Bed;
import com.uciap.medicrea.repository.AdmissionRepository;
import com.uciap.medicrea.repository.BedRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final BedRepository bedRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AdmissionService(AdmissionRepository admissionRepository,
                            BedRepository bedRepository,
                            PatientService patientService,
                            DoctorService doctorService) {
        this.admissionRepository = admissionRepository;
        this.bedRepository = bedRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public AdmissionResponse admitPatient(AdmissionRequest request) {
        patientService.getPatientEntity(request.getPatientId());
        if (request.getAttendingDoctorId() != null && !request.getAttendingDoctorId().isBlank()) {
            doctorService.getDoctorEntity(request.getAttendingDoctorId());
        }
        Bed bed = bedRepository.findById(request.getBedId())
            .orElseThrow(() -> new RuntimeException("Bed not found"));
        if (!"AVAILABLE".equalsIgnoreCase(bed.getStatus())) {
            throw new RuntimeException("Bed is not available");
        }
        bed.setStatus("OCCUPIED");
        bedRepository.save(bed);

        Admission admission = new Admission();
        admission.setPatientId(request.getPatientId());
        admission.setBedId(request.getBedId());
        admission.setAttendingDoctorId(request.getAttendingDoctorId());
        admission.setAdmittedAt(request.getAdmittedAt() != null ? request.getAdmittedAt() : LocalDateTime.now());
        admission.setStatus(request.getStatus() == null || request.getStatus().isBlank() ? "ADMITTED" : request.getStatus());
        admission.setReason(request.getReason());
        admission.setNotes(request.getNotes());
        return toResponse(admissionRepository.save(admission));
    }

    public AdmissionResponse transferAdmission(String admissionId, AdmissionTransferRequest request) {
        Admission admission = admissionRepository.findById(admissionId)
            .orElseThrow(() -> new RuntimeException("Admission not found"));
        Bed currentBed = bedRepository.findById(admission.getBedId())
            .orElseThrow(() -> new RuntimeException("Current bed not found"));
        Bed newBed = bedRepository.findById(request.getNewBedId())
            .orElseThrow(() -> new RuntimeException("New bed not found"));
        if (!"AVAILABLE".equalsIgnoreCase(newBed.getStatus())) {
            throw new RuntimeException("New bed is not available");
        }
        currentBed.setStatus("AVAILABLE");
        newBed.setStatus("OCCUPIED");
        bedRepository.save(currentBed);
        bedRepository.save(newBed);

        admission.setBedId(newBed.getId());
        admission.setStatus("TRANSFERRED");
        if (request.getNotes() != null) {
            admission.setNotes(request.getNotes());
        }
        return toResponse(admissionRepository.save(admission));
    }

    public AdmissionResponse dischargeAdmission(String admissionId, AdmissionDischargeRequest request) {
        Admission admission = admissionRepository.findById(admissionId)
            .orElseThrow(() -> new RuntimeException("Admission not found"));
        Bed bed = bedRepository.findById(admission.getBedId())
            .orElseThrow(() -> new RuntimeException("Bed not found"));
        bed.setStatus("AVAILABLE");
        bedRepository.save(bed);

        admission.setStatus("DISCHARGED");
        admission.setDischargedAt(request.getDischargedAt() != null ? request.getDischargedAt() : LocalDateTime.now());
        if (request.getNotes() != null) {
            admission.setNotes(request.getNotes());
        }
        return toResponse(admissionRepository.save(admission));
    }

    public List<AdmissionResponse> getAllAdmissions() {
        return admissionRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<AdmissionResponse> getAdmissionsForPatient(String patientId) {
        patientService.getPatientEntity(patientId);
        return admissionRepository.findByPatientId(patientId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private AdmissionResponse toResponse(Admission admission) {
        AdmissionResponse response = new AdmissionResponse();
        response.setId(admission.getId());
        response.setPatientId(admission.getPatientId());
        response.setBedId(admission.getBedId());
        response.setAttendingDoctorId(admission.getAttendingDoctorId());
        response.setAdmittedAt(admission.getAdmittedAt());
        response.setDischargedAt(admission.getDischargedAt());
        response.setStatus(admission.getStatus());
        response.setReason(admission.getReason());
        response.setNotes(admission.getNotes());
        return response;
    }
}
