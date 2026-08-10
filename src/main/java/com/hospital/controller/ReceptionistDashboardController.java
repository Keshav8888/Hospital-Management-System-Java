package com.hospital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.ReceptionistDashboardResponse;
import com.hospital.service.ReceptionistDashboardService;

@RestController
@RequestMapping("/api/receptionist/dashboard")
@Validated
public class ReceptionistDashboardController {

    private final ReceptionistDashboardService receptionistDashboardService;

    public ReceptionistDashboardController(ReceptionistDashboardService receptionistDashboardService) {

        this.receptionistDashboardService = receptionistDashboardService;
    }

    @GetMapping
    public ResponseEntity<ReceptionistDashboardResponse> getDashboard() {

        return ResponseEntity.ok(receptionistDashboardService.getDashboard());
    }
}