package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.LabOrderRequest;
import com.uciap.medicrea.dto.LabOrderResponse;
import com.uciap.medicrea.dto.LabOrderStatusRequest;
import com.uciap.medicrea.service.LabOrderService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/lab-orders")
public class LabOrderController {

    private final LabOrderService labOrderService;

    public LabOrderController(LabOrderService labOrderService) {
        this.labOrderService = labOrderService;
    }

    @PostMapping
    public LabOrderResponse createOrder(@RequestBody LabOrderRequest request) {
        return labOrderService.createOrder(request);
    }

    @GetMapping
    public List<LabOrderResponse> getOrders() {
        return labOrderService.getAllOrders();
    }

    @GetMapping("/patient/{patientId}")
    public List<LabOrderResponse> getOrdersForPatient(@PathVariable String patientId) {
        return labOrderService.getOrdersForPatient(patientId);
    }

    @PutMapping("/{orderId}/status")
    public LabOrderResponse updateStatus(@PathVariable String orderId,
                                         @RequestBody LabOrderStatusRequest request) {
        return labOrderService.updateOrderStatus(orderId, request);
    }
}
