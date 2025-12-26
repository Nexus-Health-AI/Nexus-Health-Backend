package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.PatientRequest;
import com.uciap.medicrea.dto.PatientResponse;
import com.uciap.medicrea.model.Patient;
import com.uciap.medicrea.repository.PatientRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponse createPatient(PatientRequest request) {
        Patient patient = new Patient();
        applyRequest(patient, request);
        patient.setActive(request.getActive() == null || request.getActive());
        return toResponse(patientRepository.save(patient));
    }

    public PatientResponse updatePatient(String id, PatientRequest request) {
        Patient patient = getPatientEntity(id);
        applyRequest(patient, request);
        if (request.getActive() != null) {
            patient.setActive(request.getActive());
        }
        return toResponse(patientRepository.save(patient));
    }

    public PatientResponse getPatient(String id) {
        return toResponse(getPatientEntity(id));
    }

    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll()
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public void softDeletePatient(String id) {
        Patient patient = getPatientEntity(id);
        patient.setActive(false);
        patientRepository.save(patient);
    }

    public Patient getPatientEntity(String id) {
        return patientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    private void applyRequest(Patient patient, PatientRequest request) {
        patient.setFullName(request.getFullName());
        patient.setNic(request.getNic());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setContact(request.getContact());
        patient.setAddress(request.getAddress());
    }

    private PatientResponse toResponse(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setId(patient.getId());
        response.setFullName(patient.getFullName());
        response.setNic(patient.getNic());
        response.setDateOfBirth(patient.getDateOfBirth());
        response.setGender(patient.getGender());
        response.setContact(patient.getContact());
        response.setAddress(patient.getAddress());
        response.setActive(patient.isActive());
        return response;
    }
}
