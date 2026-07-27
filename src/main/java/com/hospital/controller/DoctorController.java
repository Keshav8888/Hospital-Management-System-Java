package com.hospital.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hospital.dto.DoctorRegisterRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.dto.DoctorUpdateRequest;
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
    
    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {

        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                doctorService.getDoctorById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorUpdateRequest request) {

        doctorService.updateDoctor(id, request);

        return ResponseEntity.ok("Doctor updated successfully.");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(
            @PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return ResponseEntity.ok("Doctor deactivated successfully.");
    }

}