package com.uciap.medicrea.dto;

import java.time.LocalDateTime;

public class AdmissionRequest {

    private String patientId;
    private String bedId;
    private String attendingDoctorId;
    private LocalDateTime admittedAt;
    private String status;
    private String reason;
    private String notes;

    public AdmissionRequest() {
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getAttendingDoctorId() {
        return attendingDoctorId;
    }

    public void setAttendingDoctorId(String attendingDoctorId) {
        this.attendingDoctorId = attendingDoctorId;
    }

    public LocalDateTime getAdmittedAt() {
        return admittedAt;
    }

    public void setAdmittedAt(LocalDateTime admittedAt) {
        this.admittedAt = admittedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
