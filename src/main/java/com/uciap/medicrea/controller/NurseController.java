package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.NurseRequest;
import com.uciap.medicrea.dto.NurseResponse;
import com.uciap.medicrea.service.NurseService;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @PostMapping("/nurses")
    public NurseResponse createNurse(@RequestBody NurseRequest request) {
        return nurseService.createNurse(request);
    }

    @GetMapping("/nurses")
    public List<NurseResponse> getNurses() {
        return nurseService.getAllNurses();
    }
}
