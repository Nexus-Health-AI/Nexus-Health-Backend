package com.uciap.medicrea.dto;

import java.time.LocalDate;

public class BloodUnitResponse {

    private String id;
    private String donorId;
    private String bloodGroup;
    private LocalDate collectedDate;
    private LocalDate expiryDate;
    private String status;
    private Integer quantityMl;
    private String notes;

    public BloodUnitResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDate getCollectedDate() {
        return collectedDate;
    }

    public void setCollectedDate(LocalDate collectedDate) {
        this.collectedDate = collectedDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuantityMl() {
        return quantityMl;
    }

    public void setQuantityMl(Integer quantityMl) {
        this.quantityMl = quantityMl;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
