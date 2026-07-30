package com.hospital.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class AppointmentBookingRequest {

	@NotNull(message = "Doctor is required.")
	private Long doctorId;
	
	@NotNull(message = "Department is required.")
	private Long departmentId;
	
	@NotNull(message = "Appointment date is required.")
	@FutureOrPresent(message = "Appointment date cannot be in the past.")
	private LocalDate appointmentDate;
	
	@NotNull(message = "Appointment time is required.")
	private LocalTime appointmentTime;
	
	@NotBlank(message = "Reason is required.")
	@Size(max = 500, message = "Reason cannot exceed 500 characters.")
	private String reason;
	
	@Size(max = 1000, message = "Symptoms cannot exceed 1000 characters.")
	private String symptoms;

	public AppointmentBookingRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppointmentBookingRequest(@NotNull(message = "Doctor is required.") Long doctorId,
			@NotNull(message = "Department is required.") Long departmentId,
			@NotNull(message = "Appointment date is required.") @FutureOrPresent(message = "Appointment date cannot be in the past.") LocalDate appointmentDate,
			@NotNull(message = "Appointment time is required.") LocalTime appointmentTime,
			@NotBlank(message = "Reason is required.") @Size(max = 500, message = "Reason cannot exceed 500 characters.") String reason,
			@Size(max = 1000, message = "Symptoms cannot exceed 1000 characters.") String symptoms) {
		super();
		this.doctorId = doctorId;
		this.departmentId = departmentId;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.reason = reason;
		this.symptoms = symptoms;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public LocalTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}
	
	
}
