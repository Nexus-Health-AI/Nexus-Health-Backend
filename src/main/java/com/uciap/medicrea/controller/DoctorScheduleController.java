package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.DoctorScheduleRequest;
import com.uciap.medicrea.dto.DoctorScheduleResponse;
import com.uciap.medicrea.service.DoctorScheduleService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/doctor-schedules")
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    public DoctorScheduleController(DoctorScheduleService doctorScheduleService) {
        this.doctorScheduleService = doctorScheduleService;
    }

    @PostMapping
    public DoctorScheduleResponse createSchedule(@RequestBody DoctorScheduleRequest request) {
        return doctorScheduleService.createSchedule(request);
    }

    @GetMapping
    public List<DoctorScheduleResponse> getSchedules() {
        return doctorScheduleService.getAllSchedules();
    }

    @GetMapping("/doctor/{doctorId}")
    public List<DoctorScheduleResponse> getSchedulesForDoctor(@PathVariable String doctorId) {
        return doctorScheduleService.getSchedulesForDoctor(doctorId);
    }
}
