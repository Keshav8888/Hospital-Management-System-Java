package com.hospital.entity;

import java.time.LocalDate;

import com.hospital.enums.Gender;
import com.hospital.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "receptionists")
public class Receptionist {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	
	 	private String firstName;
	 	private String lastName;
	 	
	 	@Enumerated(EnumType.STRING)
	 	private Gender gender;
	 	
	 	@OneToOne
	 	@JoinColumn(name = "user_id", nullable = false, unique = true)
	 	private User user;

	 	private LocalDate dateOfBirth;

	 	private String phone;

	 	private String address;

	 	@Enumerated(EnumType.STRING)
	 	private Status status;

		public Receptionist() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Receptionist(Long id, String firstName, String lastName, Gender gender, User user, LocalDate dateOfBirth,
				String phone, String address, Status status) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.gender = gender;
			this.user = user;
			this.dateOfBirth = dateOfBirth;
			this.phone = phone;
			this.address = address;
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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Status getStatus() {
			return status;
		}

		public void setStatus(Status status) {
			this.status = status;
		}
	 	
	 	
}
