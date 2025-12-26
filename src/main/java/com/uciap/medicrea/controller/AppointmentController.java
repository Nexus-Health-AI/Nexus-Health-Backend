package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.AppointmentRequest;
import com.uciap.medicrea.dto.AppointmentResponse;
import com.uciap.medicrea.dto.StatusUpdateRequest;
import com.uciap.medicrea.service.AppointmentService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public AppointmentResponse createAppointment(@RequestBody AppointmentRequest request) {
        return appointmentService.createAppointment(request);
    }

    @GetMapping
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentResponse> getAppointmentsForDoctor(@PathVariable String doctorId) {
        return appointmentService.getAppointmentsForDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<AppointmentResponse> getAppointmentsForPatient(@PathVariable String patientId) {
        return appointmentService.getAppointmentsForPatient(patientId);
    }

    @PutMapping("/{id}/status")
    public AppointmentResponse updateStatus(@PathVariable String id, @RequestBody StatusUpdateRequest request) {
        return appointmentService.updateStatus(id, request.getStatus());
    }
}
