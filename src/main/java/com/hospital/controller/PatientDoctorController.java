package com.hospital.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.DoctorResponse;
import com.hospital.service.DoctorService;

@RestController
@RequestMapping("/api/patient/doctors")
public class PatientDoctorController {

    private final DoctorService doctorService;

    public PatientDoctorController(DoctorService doctorService) {
        
    	this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getDoctors(@RequestParam(required = false) Long departmentId) {

        return ResponseEntity.ok(doctorService.getActiveDoctors(departmentId));
    }
}