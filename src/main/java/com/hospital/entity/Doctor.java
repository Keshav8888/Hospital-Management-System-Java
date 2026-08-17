package com.hospital.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hospital.enums.Gender;
import com.hospital.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;
	
	@Column(nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(nullable = false, unique = true)
	private String phone;
	
	@Column(nullable = false)
	private String qualification;
	
	@Column(nullable = false)
	private String specialization;
	
	@Column(nullable = false)
	private Integer experience;
	
	@Column(name = "consultantion_fee", nullable = false)
	private BigDecimal consultantionFee;
	
	@Column(nullable = false)
	private String address;
	
	private String photo;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;

	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Doctor(Long id, String firstName, String lastName, Gender gender, User user, Department department,
			LocalDate dateOfBirth, String phone, String qualification, String specialization, Integer experience,
			BigDecimal consultantionFee, String address, String photo, Status status) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.user = user;
		this.department = department;
		this.dateOfBirth = dateOfBirth;
		this.phone = phone;
		this.qualification = qualification;
		this.specialization = specialization;
		this.experience = experience;
		this.consultantionFee = consultantionFee;
		this.address = address;
		this.photo = photo;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
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

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public BigDecimal getConsultantionFee() {
		return consultantionFee;
	}

	public void setConsultantionFee(BigDecimal consultantionFee) {
		this.consultantionFee = consultantionFee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
}
