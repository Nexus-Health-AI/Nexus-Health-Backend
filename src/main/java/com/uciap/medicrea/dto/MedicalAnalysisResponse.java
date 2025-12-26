package com.uciap.medicrea.dto;

import java.util.List;

public class MedicalAnalysisResponse {

    private String summary;
    private List<String> issues;
    private List<String> recommendedSpecialists;
    private String confidence;
    private String fileType;
    private String reportType;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getIssues() {
        return issues;
    }

    public void setIssues(List<String> issues) {
        this.issues = issues;
    }

    public List<String> getRecommendedSpecialists() {
        return recommendedSpecialists;
    }

    public void setRecommendedSpecialists(List<String> recommendedSpecialists) {
        this.recommendedSpecialists = recommendedSpecialists;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
