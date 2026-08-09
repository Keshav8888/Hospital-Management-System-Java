package com.hospital.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.PatientResponse;
import com.hospital.dto.PatientUpdateRequest;
import com.hospital.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/patients")
@Validated
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

//    @GetMapping
//    public ResponseEntity<List<PatientResponse>> getAllPatients() {
//
//        return ResponseEntity.ok(patientService.getAllPatients());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(
            @PathVariable Long id) {

        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientUpdateRequest request) {

        patientService.updatePatient(id, request);

        return ResponseEntity.ok("Patient updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(
            @PathVariable Long id) {

        patientService.deletePatient(id);

        return ResponseEntity.ok("Patient deactivated successfully.");
    }
    
    @GetMapping
    public ResponseEntity<Page<PatientResponse>> getPatients(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "firstName") String sortBy, @RequestParam(defaultValue = "asc") String sortDir) {

        return ResponseEntity.ok(patientService.getPatients(keyword, page, size, sortBy, sortDir));
    }

}