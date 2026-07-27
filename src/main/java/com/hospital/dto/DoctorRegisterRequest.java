package com.hospital.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hospital.enums.Gender;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DoctorRegisterRequest {

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotNull(message = "Gender is required.")
    private Gender gender;

    @NotNull(message = "Date of birth is required.")
    private LocalDate dateOfBirth;

    @Email(message = "Invalid email.")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters.")
    private String password;

    @NotBlank(message = "Phone number is required.")
    private String phone;

    @NotBlank(message = "Qualification is required.")
    private String qualification;

    @NotBlank(message = "Specialization is required.")
    private String specialization;

    @NotNull(message = "Experience is required.")
    @Min(value = 0, message = "Experience cannot be negative.")
    private Integer experience;

    @NotNull(message = "Consultation fee is required.")
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal consultationFee;

    @NotNull(message = "Department is required.")
    private Long departmentId;

    @NotBlank(message = "Address is required.")
    private String address;

    public DoctorRegisterRequest() {
    }

    public DoctorRegisterRequest(String firstName, String lastName, Gender gender,LocalDate dateOfBirth, String email, String password,String phone, String qualification,String specialization, Integer experience,BigDecimal consultationFee, Long departmentId,String address) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.qualification = qualification;
        this.specialization = specialization;
        this.experience = experience;
        this.consultationFee = consultationFee;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
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