package com.uciap.medicrea.dto;

import java.time.LocalDateTime;

public class LabOrderStatusRequest {

    private String status;
    private LocalDateTime sampleCollectedAt;
    private LocalDateTime resultReadyAt;
    private String resultId;

    public LabOrderStatusRequest() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSampleCollectedAt() {
        return sampleCollectedAt;
    }

    public void setSampleCollectedAt(LocalDateTime sampleCollectedAt) {
        this.sampleCollectedAt = sampleCollectedAt;
    }

    public LocalDateTime getResultReadyAt() {
        return resultReadyAt;
    }

    public void setResultReadyAt(LocalDateTime resultReadyAt) {
        this.resultReadyAt = resultReadyAt;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }
}
