package com.uciap.medicrea.dto;

public class NurseRequest {

    private String fullName;
    private String ward;

    public NurseRequest() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }
}
