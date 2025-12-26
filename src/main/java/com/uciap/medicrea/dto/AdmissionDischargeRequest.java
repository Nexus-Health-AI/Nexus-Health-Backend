package com.uciap.medicrea.dto;

import java.time.LocalDateTime;

public class AdmissionDischargeRequest {

    private LocalDateTime dischargedAt;
    private String notes;

    public AdmissionDischargeRequest() {
    }

    public LocalDateTime getDischargedAt() {
        return dischargedAt;
    }

    public void setDischargedAt(LocalDateTime dischargedAt) {
        this.dischargedAt = dischargedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
