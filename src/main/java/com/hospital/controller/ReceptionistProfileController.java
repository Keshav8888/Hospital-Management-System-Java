package com.hospital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hospital.dto.ReceptionistProfileUpdateRequest;
import com.hospital.dto.ReceptionistResponse;
import com.hospital.service.ReceptionistService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/receptionist")
@Validated
public class ReceptionistProfileController {

    private final ReceptionistService receptionistService;

    public ReceptionistProfileController(ReceptionistService receptionistService) {
        
    	this.receptionistService = receptionistService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ReceptionistResponse> getMyProfile(Authentication authentication) {

        return ResponseEntity.ok(receptionistService.getMyProfile(authentication));
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateMyProfile(@Valid @RequestBody ReceptionistProfileUpdateRequest request, Authentication authentication) {

        receptionistService.updateMyProfile(request, authentication);

        return ResponseEntity.ok("Profile updated successfully.");
    }
}