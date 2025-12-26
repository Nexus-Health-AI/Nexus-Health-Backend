package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.MedicalAnalysisResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MedicalAnalysisService {

    private final RestTemplate restTemplate;
    private final String aiServiceUrl;

    public MedicalAnalysisService(@Value("${ai.service.url}") String aiServiceUrl) {
        this.restTemplate = new RestTemplate();
        this.aiServiceUrl = aiServiceUrl;
    }

    public MedicalAnalysisResponse analyze(MultipartFile file, String reportType, String notes, String bodyPart) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is required for analysis");
        }

        ByteArrayResource fileResource;
        try {
            fileResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            throw new RuntimeException("Unable to read uploaded file", e);
        }

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileResource);
        if (reportType != null && !reportType.isBlank()) {
            body.add("reportType", reportType);
        }
        if (notes != null && !notes.isBlank()) {
            body.add("notes", notes);
        }
        if (bodyPart != null && !bodyPart.isBlank()) {
            body.add("bodyPart", bodyPart);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<MedicalAnalysisResponse> response = restTemplate.postForEntity(
            aiServiceUrl + "/analyze",
            requestEntity,
            MedicalAnalysisResponse.class
        );

        MedicalAnalysisResponse analysisResponse = response.getBody();
        if (analysisResponse == null) {
            throw new RuntimeException("No response from analysis service");
        }
        return analysisResponse;
    }
}
