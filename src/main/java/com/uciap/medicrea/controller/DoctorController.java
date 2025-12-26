package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.DoctorRequest;
import com.uciap.medicrea.dto.DoctorResponse;
import com.uciap.medicrea.service.DoctorService;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/doctors")
    public DoctorResponse createDoctor(@RequestBody DoctorRequest request) {
        return doctorService.createDoctor(request);
    }

    @GetMapping("/doctors")
    public List<DoctorResponse> getDoctors() {
        return doctorService.getAllDoctors();
    }
}
