package com.hospital.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.AppointmentRemarksRequest;
import com.hospital.dto.AppointmentResponse;
import com.hospital.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctor/appointments")
@Validated
public class DoctorAppointmentController {

	private final AppointmentService appointmentService;

    public DoctorAppointmentController(AppointmentService appointmentService) {

        this.appointmentService = appointmentService;
    }
    
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getMyAppointments(Authentication authentication) {

        return ResponseEntity.ok(appointmentService.getMyAppointmentsAsDoctor(authentication));
    }

    @GetMapping("/today")
    public ResponseEntity<List<AppointmentResponse>> getTodayAppointments(Authentication authentication) {

        return ResponseEntity.ok(appointmentService.getTodayAppointments(authentication));
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentHistory(Authentication authentication) {

        return ResponseEntity.ok(appointmentService.getAppointmentHistory(authentication));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentDetails(@PathVariable Long id, Authentication authentication) {

        return ResponseEntity.ok(appointmentService.getAppointmentDetailsForDoctor(id, authentication));
    }
    
    @PutMapping("/{id}/confirm")
    public ResponseEntity<String> confirmAppointment(@PathVariable Long id, Authentication authentication) {

        appointmentService.confirmAppointment(id, authentication);

        return ResponseEntity.ok("Appointment confirmed successfully.");
    }
    
    @PutMapping("/{id}/complete")
    public ResponseEntity<String> completeAppointment(@PathVariable Long id, Authentication authentication) {

        appointmentService.completeAppointment(id, authentication);

        return ResponseEntity.ok("Appointment completed successfully.");
    }
    
    @PutMapping("/{id}/remarks")
    public ResponseEntity<String> addConsultationRemarks(@PathVariable Long id, @Valid @RequestBody AppointmentRemarksRequest request, Authentication authentication) {

        appointmentService.addConsultationRemarks(id, request, authentication);

        return ResponseEntity.ok("Consultation remarks added successfully.");
    }
    
    
}
