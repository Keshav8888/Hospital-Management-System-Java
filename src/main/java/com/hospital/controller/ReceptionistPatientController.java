package com.hospital.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hospital.dto.PatientRegisterRequest;
import com.hospital.dto.PatientResponse;
import com.hospital.dto.PatientUpdateRequest;
import com.hospital.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/receptionist/patients")
@Validated
public class ReceptionistPatientController {

    private final PatientService patientService;

    public ReceptionistPatientController(PatientService patientService) {
        
    	this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<String> registerPatient(@Valid @RequestBody PatientRegisterRequest request) {

        patientService.registerPatientByReceptionist(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Patient registered successfully.");
    }
    
//    @GetMapping
//    public ResponseEntity<List<PatientResponse>> getAllPatients() {
//
//        return ResponseEntity.ok(patientService.getAllPatients());
//    }
//    
//    @GetMapping("/search")
//    public ResponseEntity<List<PatientResponse>> searchPatients(@RequestParam String keyword) {
//
//        return ResponseEntity.ok(patientService.searchPatients(keyword));
//    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {

        return ResponseEntity.ok(patientService.getPatientById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientUpdateRequest request) {

        patientService.updatePatient(id, request);

        return ResponseEntity.ok("Patient updated successfully.");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {

        patientService.deletePatient(id);

        return ResponseEntity.ok("Patient deactivated successfully.");
    }
    
    @GetMapping
    public ResponseEntity<Page<PatientResponse>> getPatients(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(patientService.getPatients(keyword, page, size));
    }
}