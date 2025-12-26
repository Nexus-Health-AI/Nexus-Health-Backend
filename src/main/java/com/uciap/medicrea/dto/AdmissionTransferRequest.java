package com.uciap.medicrea.dto;

public class AdmissionTransferRequest {

    private String newBedId;
    private String notes;

    public AdmissionTransferRequest() {
    }

    public String getNewBedId() {
        return newBedId;
    }

    public void setNewBedId(String newBedId) {
        this.newBedId = newBedId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
