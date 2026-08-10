package com.hospital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.PatientDashboardResponse;
import com.hospital.service.PatientDashboardService;

@RestController
@RequestMapping("/api/patient/dashboard")
@Validated
public class PatientDashboardController {

    private final PatientDashboardService patientDashboardService;

    public PatientDashboardController(PatientDashboardService patientDashboardService) {

        this.patientDashboardService = patientDashboardService;
    }

    @GetMapping
    public ResponseEntity<PatientDashboardResponse> getDashboard(Authentication authentication) {

        return ResponseEntity.ok(patientDashboardService.getDashboard(authentication.getName()));
    }
}