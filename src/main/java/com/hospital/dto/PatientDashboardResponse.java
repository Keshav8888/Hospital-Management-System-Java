package com.hospital.dto;

public class PatientDashboardResponse {

    private long todaysAppointments;

    private long totalAppointments;

    private long bookedAppointments;

    private long confirmedAppointments;

    private long completedAppointments;

    private long cancelledAppointments;

    public PatientDashboardResponse() {
    	
    }

    public long getTodaysAppointments() {
        return todaysAppointments;
    }

    public void setTodaysAppointments(long todaysAppointments) {
        this.todaysAppointments = todaysAppointments;
    }

    public long getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(long totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public long getBookedAppointments() {
        return bookedAppointments;
    }

    public void setBookedAppointments(long bookedAppointments) {
        this.bookedAppointments = bookedAppointments;
    }

    public long getConfirmedAppointments() {
        return confirmedAppointments;
    }

    public void setConfirmedAppointments(long confirmedAppointments) {
        this.confirmedAppointments = confirmedAppointments;
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