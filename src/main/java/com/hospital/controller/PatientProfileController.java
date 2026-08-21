package com.hospital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.PatientProfileUpdateRequest;
import com.hospital.dto.PatientResponse;
import com.hospital.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patient/profile")
@Validated
public class PatientProfileController {

    private final PatientService patientService;


    public PatientProfileController(PatientService patientService) {

        this.patientService = patientService;
    }


    @GetMapping
    public ResponseEntity<PatientResponse> getMyProfile(Authentication authentication) {

        return ResponseEntity.ok(patientService.getMyProfile(authentication));
    }


    @PutMapping
    public ResponseEntity<String> updateMyProfile(@Valid @RequestBody PatientProfileUpdateRequest request, Authentication authentication) {

        patientService.updateMyProfile(request, authentication);

        return ResponseEntity.ok("Patient profile updated successfully.");
    }
}