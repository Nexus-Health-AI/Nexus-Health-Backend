package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.LabOrderRequest;
import com.uciap.medicrea.dto.LabOrderResponse;
import com.uciap.medicrea.dto.LabOrderStatusRequest;
import com.uciap.medicrea.model.LabOrder;
import com.uciap.medicrea.repository.LabOrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class LabOrderService {

    private final LabOrderRepository labOrderRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final LabTestService labTestService;

    public LabOrderService(LabOrderRepository labOrderRepository,
                           PatientService patientService,
                           DoctorService doctorService,
                           LabTestService labTestService) {
        this.labOrderRepository = labOrderRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.labTestService = labTestService;
    }

    public LabOrderResponse createOrder(LabOrderRequest request) {
        patientService.getPatientEntity(request.getPatientId());
        if (request.getDoctorId() != null && !request.getDoctorId().isBlank()) {
            doctorService.getDoctorEntity(request.getDoctorId());
        }
        if (labTestService.getByName(request.getTestName()) == null) {
            throw new RuntimeException("Lab test not found");
        }
        LabOrder order = new LabOrder();
        order.setPatientId(request.getPatientId());
        order.setDoctorId(request.getDoctorId());
        order.setTestName(request.getTestName());
        order.setOrderedAt(request.getOrderedAt() != null ? request.getOrderedAt() : LocalDateTime.now());
        order.setStatus(request.getStatus() == null || request.getStatus().isBlank() ? "ORDERED" : request.getStatus());
        order.setSampleCollectedAt(request.getSampleCollectedAt());
        order.setResultReadyAt(request.getResultReadyAt());
        order.setPriority(request.getPriority());
        order.setResultId(request.getResultId());
        order.setNotes(request.getNotes());
        return toResponse(labOrderRepository.save(order));
    }

    public LabOrderResponse updateOrderStatus(String orderId, LabOrderStatusRequest request) {
        LabOrder order = labOrderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Lab order not found"));
        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }
        if (request.getSampleCollectedAt() != null) {
            order.setSampleCollectedAt(request.getSampleCollectedAt());
        }
        if (request.getResultReadyAt() != null) {
            order.setResultReadyAt(request.getResultReadyAt());
        }
        if (request.getResultId() != null) {
            order.setResultId(request.getResultId());
        }
        return toResponse(labOrderRepository.save(order));
    }

    public List<LabOrderResponse> getAllOrders() {
        return labOrderRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<LabOrderResponse> getOrdersForPatient(String patientId) {
        patientService.getPatientEntity(patientId);
        return labOrderRepository.findByPatientId(patientId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public LabOrderResponse updateOrderResult(String orderId, String resultId) {
        LabOrder order = labOrderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Lab order not found"));
        order.setResultId(resultId);
        order.setStatus("RESULT_READY");
        order.setResultReadyAt(LocalDateTime.now());
        return toResponse(labOrderRepository.save(order));
    }

    private LabOrderResponse toResponse(LabOrder order) {
        LabOrderResponse response = new LabOrderResponse();
        response.setId(order.getId());
        response.setPatientId(order.getPatientId());
        response.setDoctorId(order.getDoctorId());
        response.setTestName(order.getTestName());
        response.setOrderedAt(order.getOrderedAt());
        response.setStatus(order.getStatus());
        response.setSampleCollectedAt(order.getSampleCollectedAt());
        response.setResultReadyAt(order.getResultReadyAt());
        response.setPriority(order.getPriority());
        response.setResultId(order.getResultId());
        response.setNotes(order.getNotes());
        return response;
    }
}
