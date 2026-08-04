package com.hospital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.DoctorProfileUpdateRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctor/profile")
@Validated
public class DoctorProfileController {

    private final DoctorService doctorService;

    public DoctorProfileController(DoctorService doctorService) {

        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<DoctorResponse> getMyProfile(
            Authentication authentication) {

        return ResponseEntity.ok(
                doctorService.getMyProfile(authentication));
    }

    @PutMapping
    public ResponseEntity<String> updateMyProfile(
            @Valid @RequestBody DoctorProfileUpdateRequest request,
            Authentication authentication) {

        doctorService.updateMyProfile(request, authentication);

        return ResponseEntity.ok(
                "Doctor profile updated successfully.");
    }
}