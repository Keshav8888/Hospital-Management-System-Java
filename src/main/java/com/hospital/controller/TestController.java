package com.hospital.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/api/patient/profile")
	public String patientProfile() {
		return "Welcome Patient!";
	}
	
	@GetMapping("/api/admin/dashboard")
	public String adminDashboard() {
		return "Welcome Admin!";
	}
	
	@GetMapping("/api/doctor/dashboard")
	public String doctorDashboard() {
		return "Welcome Doctor!";
	}
	
	@GetMapping("/api/receptionist/dashboard")
	public String receptionistDashboard() {
		return "Welcome Receptionist!";
	}
}
