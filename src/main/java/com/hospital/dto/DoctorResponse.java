package com.hospital.dto;

import java.math.BigDecimal;

public class DoctorResponse {

	private Long id;
	private String firstName;
	private String lastName;
	private Long departmentId;
	private String department;
	private String specialization;
	private String qualification;
	private Integer experience;
	private BigDecimal ConsultantionFee;
	private String phone;
	private String email;
	private String status;
	
	
	public DoctorResponse() {
		super();
	}


	public DoctorResponse(Long id, String firstName, String lastName, Long departmentId, String department, String specialization,
			String qualification, Integer experience, BigDecimal ConsultantionFee, String phone, String email,
			String status) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.departmentId = departmentId;
		this.department = department;
		this.specialization = specialization;
		this.qualification = qualification;
		this.experience = experience;
		this.ConsultantionFee = ConsultantionFee;
		this.phone = phone;
		this.email = email;
		this.status = status;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getDepartmentId() {
	    return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
	    this.departmentId = departmentId;
	}
	
	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getSpecialization() {
		return specialization;
	}


	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}


	public String getQualification() {
		return qualification;
	}


	public void setQualification(String qualification) {
		this.qualification = qualification;
	}


	public Integer getExperience() {
		return experience;
	}


	public void setExperience(Integer experience) {
		this.experience = experience;
	}


	public BigDecimal getConsultantionFee() {
		return ConsultantionFee;
	}


	public void setConsultantionFee(BigDecimal ConsultantionFee) {
		this.ConsultantionFee = ConsultantionFee;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
