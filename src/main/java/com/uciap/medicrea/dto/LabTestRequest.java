package com.uciap.medicrea.dto;

public class LabTestRequest {

    private String testName;
    private String normalRange;

    public LabTestRequest() {
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
    }
}
