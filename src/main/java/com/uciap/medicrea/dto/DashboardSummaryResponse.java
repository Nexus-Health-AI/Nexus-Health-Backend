package com.uciap.medicrea.dto;

public class DashboardSummaryResponse {

    private long totalPatients;
    private long totalDoctors;
    private long totalNurses;
    private long totalAppointments;
    private long activeAdmissions;
    private long totalBeds;
    private long occupiedBeds;
    private long pendingLabOrders;
    private long lowStockMedicines;
    private long activeEmergencyCases;
    private long availableBloodUnits;
    private long pendingInsuranceClaims;

    public DashboardSummaryResponse() {
    }

    public long getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(long totalPatients) {
        this.totalPatients = totalPatients;
    }

    public long getTotalDoctors() {
        return totalDoctors;
    }

    public void setTotalDoctors(long totalDoctors) {
        this.totalDoctors = totalDoctors;
    }

    public long getTotalNurses() {
        return totalNurses;
    }

    public void setTotalNurses(long totalNurses) {
        this.totalNurses = totalNurses;
    }

    public long getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(long totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public long getActiveAdmissions() {
        return activeAdmissions;
    }

    public void setActiveAdmissions(long activeAdmissions) {
        this.activeAdmissions = activeAdmissions;
    }

    public long getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(long totalBeds) {
        this.totalBeds = totalBeds;
    }

    public long getOccupiedBeds() {
        return occupiedBeds;
    }

    public void setOccupiedBeds(long occupiedBeds) {
        this.occupiedBeds = occupiedBeds;
    }

    public long getPendingLabOrders() {
        return pendingLabOrders;
    }

    public void setPendingLabOrders(long pendingLabOrders) {
        this.pendingLabOrders = pendingLabOrders;
    }

    public long getLowStockMedicines() {
        return lowStockMedicines;
    }

    public void setLowStockMedicines(long lowStockMedicines) {
        this.lowStockMedicines = lowStockMedicines;
    }

    public long getActiveEmergencyCases() {
        return activeEmergencyCases;
    }

    public void setActiveEmergencyCases(long activeEmergencyCases) {
        this.activeEmergencyCases = activeEmergencyCases;
    }

    public long getAvailableBloodUnits() {
        return availableBloodUnits;
    }

    public void setAvailableBloodUnits(long availableBloodUnits) {
        this.availableBloodUnits = availableBloodUnits;
    }

    public long getPendingInsuranceClaims() {
        return pendingInsuranceClaims;
    }

    public void setPendingInsuranceClaims(long pendingInsuranceClaims) {
        this.pendingInsuranceClaims = pendingInsuranceClaims;
    }
}
