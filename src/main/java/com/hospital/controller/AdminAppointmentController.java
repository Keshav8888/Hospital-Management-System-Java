package com.hospital.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hospital.dto.AppointmentRescheduleRequest;
import com.hospital.dto.AppointmentResponse;
import com.hospital.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/appointments")
@Validated
public class AdminAppointmentController {

    private final AppointmentService appointmentService;

    public AdminAppointmentController(AppointmentService appointmentService) {

        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentResponse>> getAppointments(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(appointmentService.getAppointments(keyword, page, size));
    }

    @GetMapping("/today")
    public ResponseEntity<List<AppointmentResponse>> getTodaysAppointments() {

        return ResponseEntity.ok(appointmentService.getTodaysAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {

        return ResponseEntity.ok(appointmentService.getAppointmentDetailsForAdmin(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {

        appointmentService.cancelAppointment(id);

        return ResponseEntity.ok("Appointment cancelled successfully.");
    }

    @PutMapping("/{id}/reschedule")
    public ResponseEntity<AppointmentResponse> rescheduleAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentRescheduleRequest request) {

        AppointmentResponse response = appointmentService.rescheduleAppointment(id, request);

        return ResponseEntity.ok(response);
    }
}