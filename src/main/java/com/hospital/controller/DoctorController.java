package com.hospital.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hospital.dto.DoctorRegisterRequest;
import com.hospital.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/doctors")
@Validated
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<String> registerDoctor(
            @Valid @RequestBody DoctorRegisterRequest request) {

        doctorService.registerDoctor(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Doctor registered successfully.");
    }

}