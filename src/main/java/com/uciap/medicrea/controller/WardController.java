package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.WardRequest;
import com.uciap.medicrea.dto.WardResponse;
import com.uciap.medicrea.service.WardService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/wards")
public class WardController {

    private final WardService wardService;

    public WardController(WardService wardService) {
        this.wardService = wardService;
    }

    @PostMapping
    public WardResponse createWard(@RequestBody WardRequest request) {
        return wardService.createWard(request);
    }

    @GetMapping
    public List<WardResponse> getWards() {
        return wardService.getAllWards();
    }
}
