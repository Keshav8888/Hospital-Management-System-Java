package com.hospital.dto;

import java.time.LocalDate;

import com.hospital.enums.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PatientUpdateRequest {

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotNull(message = "Gender is required.")
    private Gender gender;

    @NotNull(message = "Date of birth is required.")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number.")
    private String phone;

    @NotBlank(message = "Blood group is required.")
    private String bloodGroup;

    @NotBlank(message = "Address is required.")
    private String address;

    @NotBlank(message = "Emergency contact is required.")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid emergency contact number.")
    private String emergencyContact;

    private String allergies;

    private String medicalHistory;

    public PatientUpdateRequest() {
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
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