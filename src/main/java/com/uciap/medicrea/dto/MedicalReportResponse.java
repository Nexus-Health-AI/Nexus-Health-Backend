package com.uciap.medicrea.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class MedicalReportResponse {

    private String id;
    private String patientId;
    private String filePath;
    private String hash;
    private LocalDateTime createdAt;
    private String html;

    public MedicalReportResponse() {
    }

}
