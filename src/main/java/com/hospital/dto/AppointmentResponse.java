package com.hospital.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.hospital.enums.AppointmentStatus;


public class AppointmentResponse {

	private Long id;
	private String appointmentNumber;
	private Long patientId;
	private String patientName;
	private Long doctorId;
	private String doctorName;
	private Long departmentId;
	private String departmentName;
	private LocalDate appointmentDate;
	private LocalTime appointmentTime;
	private String reason;
	private String symptoms;
	private AppointmentStatus status;
	private String remarks;
	private LocalDateTime createdAt;
	
	
	public AppointmentResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AppointmentResponse(Long id, String appointmentNumber, Long patientId, String patientName, Long doctorId,
			String doctorName, Long departmentId, String departmentName, LocalDate appointmentDate,
			LocalTime appointmentTime, String reason, String symptoms, AppointmentStatus status, String remarks,
			LocalDateTime createdAt) {
		super();
		this.id = id;
		this.appointmentNumber = appointmentNumber;
		this.patientId = patientId;
		this.patientName = patientName;
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.reason = reason;
		this.symptoms = symptoms;
		this.status = status;
		this.remarks = remarks;
		this.createdAt = createdAt;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAppointmentNumber() {
		return appointmentNumber;
	}


	public void setAppointmentNumber(String appointmentNumber) {
		this.appointmentNumber = appointmentNumber;
	}


	public Long getPatientId() {
		return patientId;
	}


	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}


	public String getPatientName() {
		return patientName;
	}


	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}


	public Long getDoctorId() {
		return doctorId;
	}


	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}


	public String getDoctorName() {
		return doctorName;
	}


	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}


	public Long getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}


	public String getDepartmentName() {
		return departmentName;
	}


	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
