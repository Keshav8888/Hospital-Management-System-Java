package com.hospital.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.ReceptionistRegisterRequest;
import com.hospital.dto.ReceptionistResponse;
import com.hospital.dto.ReceptionistUpdateRequest;
import com.hospital.service.ReceptionistService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/receptionists")
@Validated
public class ReceptionistController {

    private final ReceptionistService receptionistService;

    public ReceptionistController(ReceptionistService receptionistService) {
        
    	this.receptionistService = receptionistService;
    }
	
    @PostMapping
    public ResponseEntity<String> registerReceptionist(@Valid @RequestBody ReceptionistRegisterRequest request) {

        receptionistService.registerReceptionist(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Receptionist registered successfully.");
    }
    
//    @GetMapping
//    public ResponseEntity<List<ReceptionistResponse>> getAllReceptionists() {
//
//        return ResponseEntity.ok(receptionistService.getAllReceptionists());
//    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ReceptionistResponse> getReceptionistById(@PathVariable Long id) {

        return ResponseEntity.ok(receptionistService.getReceptionistById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateReceptionist(@PathVariable Long id, @Valid @RequestBody ReceptionistUpdateRequest request) {

        receptionistService.updateReceptionist(id, request);

        return ResponseEntity.ok("Receptionist updated successfully.");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReceptionist(@PathVariable Long id) {

        receptionistService.deleteReceptionist(id);

        return ResponseEntity.ok("Receptionist deactivated successfully.");
    }
    
    @GetMapping
    public ResponseEntity<Page<ReceptionistResponse>> getReceptionists(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(receptionistService.getReceptionists(keyword, page, size));
    }
    
}
