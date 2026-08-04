package com.hospital.dto;

public class AdminDashboardResponse {

    private long totalDoctors;

    private long totalPatients;

    private long totalReceptionists;

    private long totalDepartments;

    private long totalAppointments;

    private long todaysAppointments;

    private long completedAppointments;

    private long cancelledAppointments;

    public AdminDashboardResponse() {
    	
    }

    public long getTotalDoctors() {
        return totalDoctors;
    }

    public void setTotalDoctors(long totalDoctors) {
        this.totalDoctors = totalDoctors;
    }

    public long getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(long totalPatients) {
        this.totalPatients = totalPatients;
    }

    public long getTotalReceptionists() {
        return totalReceptionists;
    }

    public void setTotalReceptionists(long totalReceptionists) {
        this.totalReceptionists = totalReceptionists;
    }

    public long getTotalDepartments() {
        return totalDepartments;
    }

    public void setTotalDepartments(long totalDepartments) {
        this.totalDepartments = totalDepartments;
    }

    public long getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(long totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public long getTodaysAppointments() {
        return todaysAppointments;
    }

    public void setTodaysAppointments(long todaysAppointments) {
        this.todaysAppointments = todaysAppointments;
    }

    public long getCompletedAppointments() {
        return completedAppointments;
    }

    public void setCompletedAppointments(long completedAppointments) {
        this.completedAppointments = completedAppointments;
    }

    public long getCancelledAppointments() {
        return cancelledAppointments;
    }

    public void setCancelledAppointments(long cancelledAppointments) {
        this.cancelledAppointments = cancelledAppointments;
    }
}