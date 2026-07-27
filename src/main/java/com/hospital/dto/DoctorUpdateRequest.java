package com.hospital.dto;

import java.math.BigDecimal;

import com.hospital.enums.Gender;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DoctorUpdateRequest {

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotNull(message = "Gender is required.")
    private Gender gender;

    @NotBlank(message = "Phone number is required.")
    private String phone;

    @NotBlank(message = "Qualification is required.")
    private String qualification;

    @NotBlank(message = "Specialization is required.")
    private String specialization;

    @NotNull(message = "Experience is required.")
    @Min(0)
    private Integer experience;

    @NotNull(message = "Consultation fee is required.")
    @DecimalMin(value = "0.0")
    private BigDecimal consultantion_Fee;

    @NotNull(message = "Department is required.")
    private Long departmentId;

    @NotBlank(message = "Address is required.")
    private String address;


    public DoctorUpdateRequest() {
    	
    }


	public DoctorUpdateRequest(@NotBlank(message = "First name is required.") String firstName,
			@NotBlank(message = "Last name is required.") String lastName,
			@NotNull(message = "Gender is required.") Gender gender,
			@NotBlank(message = "Phone number is required.") String phone,
			@NotBlank(message = "Qualification is required.") String qualification,
			@NotBlank(message = "Specialization is required.") String specialization,
			@NotNull(message = "Experience is required.") @Min(0) Integer experience,
			@NotNull(message = "Consultation fee is required.") @DecimalMin("0.0") BigDecimal consultantion_Fee,
			@NotNull(message = "Department is required.") Long departmentId,
			@NotBlank(message = "Address is required.") String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.phone = phone;
		this.qualification = qualification;
		this.specialization = specialization;
		this.experience = experience;
		this.consultantion_Fee = consultantion_Fee;
		this.departmentId = departmentId;
		this.address = address;
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


	public Gender getGender() {
		return gender;
	}


	public void setGender(Gender gender) {
		this.gender = gender;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getQualification() {
		return qualification;
	}


	public void setQualification(String qualification) {
		this.qualification = qualification;
	}


	public String getSpecialization() {
		return specialization;
	}


	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}


	public Integer getExperience() {
		return experience;
	}


	public void setExperience(Integer experience) {
		this.experience = experience;
	}


	public BigDecimal getconsultantion_Fee() {
		return consultantion_Fee;
	}


	public void setconsultantion_Fee(BigDecimal consultantion_Fee) {
		this.consultantion_Fee = consultantion_Fee;
	}


	public Long getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}
    
    

}