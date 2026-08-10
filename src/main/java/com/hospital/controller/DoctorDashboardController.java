package com.hospital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.DoctorDashboardResponse;
import com.hospital.service.DoctorDashboardService;

@RestController
@RequestMapping("/api/doctor/dashboard")
@Validated
public class DoctorDashboardController {

    private final DoctorDashboardService doctorDashboardService;

    public DoctorDashboardController(DoctorDashboardService doctorDashboardService) {

        this.doctorDashboardService = doctorDashboardService;
    }

    @GetMapping
    public ResponseEntity<DoctorDashboardResponse> getDashboard(Authentication authentication) {

        return ResponseEntity.ok(doctorDashboardService.getDashboard(authentication.getName()));
    }
}