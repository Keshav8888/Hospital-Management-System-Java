package com.hospital.dto;

import java.time.LocalDate;

import com.hospital.enums.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DoctorProfileUpdateRequest {

    @NotBlank(message = "First name is required.")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 50)
    private String lastName;

    @NotNull(message = "Gender is required.")
    private Gender gender;

    @NotNull(message = "Date of birth is required.")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must contain exactly 10 digits.")
    private String phone;

    @NotBlank(message = "Qualification is required.")
    private String qualification;

    @NotBlank(message = "Specialization is required.")
    private String specialization;

    @NotBlank(message = "Address is required.")
    private String address;

    public DoctorProfileUpdateRequest() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}