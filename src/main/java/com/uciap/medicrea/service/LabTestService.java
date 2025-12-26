package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.LabTestRequest;
import com.uciap.medicrea.dto.LabTestResponse;
import com.uciap.medicrea.model.LabTest;
import com.uciap.medicrea.repository.LabTestRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class LabTestService {

    private final LabTestRepository labTestRepository;

    public LabTestService(LabTestRepository labTestRepository) {
        this.labTestRepository = labTestRepository;
    }

    public LabTestResponse createLabTest(LabTestRequest request) {
        LabTest labTest = new LabTest();
        labTest.setTestName(request.getTestName());
        labTest.setNormalRange(request.getNormalRange());
        return toResponse(labTestRepository.save(labTest));
    }

    public List<LabTestResponse> getAllTests() {
        return labTestRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public LabTest getByName(String testName) {
        return labTestRepository.findByTestName(testName).orElse(null);
    }

    private LabTestResponse toResponse(LabTest labTest) {
        LabTestResponse response = new LabTestResponse();
        response.setId(labTest.getId());
        response.setTestName(labTest.getTestName());
        response.setNormalRange(labTest.getNormalRange());
        return response;
    }
}
