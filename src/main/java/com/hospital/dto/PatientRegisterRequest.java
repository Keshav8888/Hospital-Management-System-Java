package com.hospital.dto;

import java.time.LocalDate;

import com.hospital.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PatientRegisterRequest {

	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 8)
	private String password;
	
	@NotBlank
	private String phone;
	
	private Gender gender;
	private LocalDate dateOfBirth;
	private String emergencyContact;
	private String bloodGroup;
	private String address;
	private String allergies;
	private String medicalHistory;
	
	
	public PatientRegisterRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PatientRegisterRequest(@NotBlank String firstName, @NotBlank String lastName, @Email String email,
			@NotBlank @Size(min = 8) String password, @NotBlank String phone, Gender gender, LocalDate dateOfBirth,
			String emergencyContact, String bloodGroup, String address, String allergies, String medicalHistory) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.emergencyContact = emergencyContact;
		this.bloodGroup = bloodGroup;
		this.address = address;
		this.allergies = allergies;
		this.medicalHistory = medicalHistory;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Gender getGender() {
		return gender;
	}


	public void setGender(Gender gender) {
		this.gender = gender;
	}


	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getEmergencyContact() {
		return emergencyContact;
	}


	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}


	public String getBloodGroup() {
		return bloodGroup;
	}


	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getAllergies() {
		return allergies;
	}


	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}


	public String getMedicalHistory() {
		return medicalHistory;
	}


	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	
}
