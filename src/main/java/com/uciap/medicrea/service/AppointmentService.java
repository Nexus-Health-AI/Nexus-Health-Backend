package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.AppointmentRequest;
import com.uciap.medicrea.dto.AppointmentResponse;
import com.uciap.medicrea.model.Appointment;
import com.uciap.medicrea.repository.AppointmentRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientService patientService,
                              DoctorService doctorService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        patientService.getPatientEntity(request.getPatientId());
        doctorService.getDoctorEntity(request.getDoctorId());

        Appointment appointment = new Appointment();
        appointment.setPatientId(request.getPatientId());
        appointment.setDoctorId(request.getDoctorId());
        appointment.setAppointmentDate(request.getAppointmentDate() != null
            ? request.getAppointmentDate() : LocalDateTime.now());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus() == null || request.getStatus().isBlank()
            ? "SCHEDULED" : request.getStatus());
        appointment.setNotes(request.getNotes());
        return toResponse(appointmentRepository.save(appointment));
    }

    public AppointmentResponse updateStatus(String id, String status) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(status);
        return toResponse(appointmentRepository.save(appointment));
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<AppointmentResponse> getAppointmentsForDoctor(String doctorId) {
        doctorService.getDoctorEntity(doctorId);
        return appointmentRepository.findByDoctorId(doctorId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<AppointmentResponse> getAppointmentsForPatient(String patientId) {
        patientService.getPatientEntity(patientId);
        return appointmentRepository.findByPatientId(patientId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private AppointmentResponse toResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setPatientId(appointment.getPatientId());
        response.setDoctorId(appointment.getDoctorId());
        response.setAppointmentDate(appointment.getAppointmentDate());
        response.setReason(appointment.getReason());
        response.setStatus(appointment.getStatus());
        response.setNotes(appointment.getNotes());
        return response;
    }
}
