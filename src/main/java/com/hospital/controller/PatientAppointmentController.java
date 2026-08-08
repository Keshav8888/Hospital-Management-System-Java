package com.hospital.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hospital.dto.AppointmentBookingRequest;
import com.hospital.dto.AppointmentResponse;
import com.hospital.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patient/appointments")
@Validated
public class PatientAppointmentController {

    private final AppointmentService appointmentService;

    public PatientAppointmentController(AppointmentService appointmentService) {

        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> bookAppointment(@Valid @RequestBody AppointmentBookingRequest request, Authentication authentication) {

        AppointmentResponse response = appointmentService.bookAppointment(request, authentication);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getMyAppointments(Authentication authentication) {

        return ResponseEntity.ok(appointmentService.getMyAppointments(authentication));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getMyAppointmentById(@PathVariable Long id, Authentication authentication) {

        return ResponseEntity.ok(appointmentService.getMyAppointmentById(id, authentication));
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id, Authentication authentication) {

        appointmentService.cancelAppointment(id, authentication);

        return ResponseEntity.ok("Appointment cancelled successfully.");
    }
    
    
}