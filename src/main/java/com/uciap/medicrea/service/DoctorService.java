package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.DoctorRequest;
import com.uciap.medicrea.dto.DoctorResponse;
import com.uciap.medicrea.model.Doctor;
import com.uciap.medicrea.repository.DoctorRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public DoctorResponse createDoctor(DoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setFullName(request.getFullName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setLicenseNumber(request.getLicenseNumber());
        return toResponse(doctorRepository.save(doctor));
    }

    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public Doctor getDoctorEntity(String id) {
        return doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    private DoctorResponse toResponse(Doctor doctor) {
        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getId());
        response.setFullName(doctor.getFullName());
        response.setSpecialization(doctor.getSpecialization());
        response.setLicenseNumber(doctor.getLicenseNumber());
        return response;
    }
}
