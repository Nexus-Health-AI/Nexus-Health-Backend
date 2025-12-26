package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.LabResultRequest;
import com.uciap.medicrea.dto.LabResultResponse;
import com.uciap.medicrea.model.LabResult;
import com.uciap.medicrea.model.LabTest;
import com.uciap.medicrea.repository.LabResultRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class LabResultService {

    private final LabResultRepository labResultRepository;
    private final PatientService patientService;
    private final LabTestService labTestService;
    private final LabOrderService labOrderService;

    public LabResultService(LabResultRepository labResultRepository,
                            PatientService patientService,
                            LabTestService labTestService,
                            LabOrderService labOrderService) {
        this.labResultRepository = labResultRepository;
        this.patientService = patientService;
        this.labTestService = labTestService;
        this.labOrderService = labOrderService;
    }

    public LabResultResponse createLabResult(LabResultRequest request) {
        patientService.getPatientEntity(request.getPatientId());
        LabTest labTest = labTestService.getByName(request.getTestName());
        if (labTest == null) {
            throw new RuntimeException("Lab test not found");
        }
        LabResult result = new LabResult();
        result.setPatientId(request.getPatientId());
        result.setOrderId(request.getOrderId());
        result.setTestName(request.getTestName());
        result.setResult(request.getResult());
        result.setResultDate(request.getResultDate() != null ? request.getResultDate() : java.time.LocalDate.now());
        LabResult saved = labResultRepository.save(result);
        if (request.getOrderId() != null && !request.getOrderId().isBlank()) {
            labOrderService.updateOrderResult(request.getOrderId(), saved.getId());
        }
        return toResponse(saved, labTest);
    }

    public List<LabResultResponse> getLabResultsForPatient(String patientId) {
        patientService.getPatientEntity(patientId);
        return labResultRepository.findByPatientId(patientId).stream()
            .map(result -> {
                LabTest labTest = labTestService.getByName(result.getTestName());
                return toResponse(result, labTest);
            })
            .collect(Collectors.toList());
    }

    public List<LabResult> getLabResultEntitiesForPatient(String patientId) {
        patientService.getPatientEntity(patientId);
        return labResultRepository.findByPatientId(patientId);
    }

    private LabResultResponse toResponse(LabResult result, LabTest labTest) {
        LabResultResponse response = new LabResultResponse();
        response.setId(result.getId());
        response.setPatientId(result.getPatientId());
        response.setOrderId(result.getOrderId());
        response.setTestName(result.getTestName());
        response.setResult(result.getResult());
        response.setResultDate(result.getResultDate());
        response.setNormalRange(labTest != null ? labTest.getNormalRange() : "N/A");
        return response;
    }
}
