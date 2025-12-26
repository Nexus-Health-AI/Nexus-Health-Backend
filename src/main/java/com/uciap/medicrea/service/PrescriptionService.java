package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.PrescriptionRequest;
import com.uciap.medicrea.dto.PrescriptionResponse;
import com.uciap.medicrea.model.Prescription;
import com.uciap.medicrea.repository.PrescriptionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               PatientService patientService,
                               DoctorService doctorService) {
        this.prescriptionRepository = prescriptionRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public PrescriptionResponse createPrescription(PrescriptionRequest request) {
        validateReferences(request.getPatientId(), request.getDoctorId());
        Prescription prescription = new Prescription();
        prescription.setPatientId(request.getPatientId());
        prescription.setDoctorId(request.getDoctorId());
        prescription.setMedicineName(request.getMedicineName());
        prescription.setDosage(request.getDosage());
        prescription.setFrequency(request.getFrequency());
        prescription.setIssuedDate(request.getIssuedDate());
        return toResponse(prescriptionRepository.save(prescription));
    }

    public List<PrescriptionResponse> getPrescriptionsForPatient(String patientId) {
        patientService.getPatientEntity(patientId);
        return prescriptionRepository.findByPatientId(patientId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<Prescription> getPrescriptionEntitiesForPatient(String patientId) {
        patientService.getPatientEntity(patientId);
        return prescriptionRepository.findByPatientId(patientId);
    }

    private void validateReferences(String patientId, String doctorId) {
        patientService.getPatientEntity(patientId);
        doctorService.getDoctorEntity(doctorId);
    }

    private PrescriptionResponse toResponse(Prescription prescription) {
        PrescriptionResponse response = new PrescriptionResponse();
        response.setId(prescription.getId());
        response.setPatientId(prescription.getPatientId());
        response.setDoctorId(prescription.getDoctorId());
        response.setMedicineName(prescription.getMedicineName());
        response.setDosage(prescription.getDosage());
        response.setFrequency(prescription.getFrequency());
        response.setIssuedDate(prescription.getIssuedDate());
        return response;
    }
}
