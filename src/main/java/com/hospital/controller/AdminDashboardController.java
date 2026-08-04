package com.hospital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.AdminDashboardResponse;
import com.hospital.service.AdminDashboardService;

@RestController
@RequestMapping("/api/admin/dashboard")
@Validated
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    public AdminDashboardController(AdminDashboardService adminDashboardService) {

        this.adminDashboardService = adminDashboardService;
    }

    @GetMapping
    public ResponseEntity<AdminDashboardResponse> getDashboard() {

        return ResponseEntity.ok(adminDashboardService.getDashboard());
    }
}