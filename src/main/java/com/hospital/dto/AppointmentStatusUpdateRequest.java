package com.hospital.dto;

import com.hospital.enums.AppointmentStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AppointmentStatusUpdateRequest {

	@NotNull(message = "Appointment status is required.")
	private AppointmentStatus status;
	
	@Size(max = 1000, message = "Remarks cannot exceed 1000 characters.")
	private String remarks;

	public AppointmentStatusUpdateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppointmentStatusUpdateRequest(
			@NotNull(message = "Appointment status is required.") AppointmentStatus status,
			@Size(max = 1000, message = "Remarks cannot exceed 1000 characters.") String remarks) {
		super();
		this.status = status;
		this.remarks = remarks;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
