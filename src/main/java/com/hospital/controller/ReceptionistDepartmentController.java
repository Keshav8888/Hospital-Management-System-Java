package com.hospital.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.DepartmentResponse;
import com.hospital.service.DepartmentService;

@RestController
@RequestMapping("/api/receptionist/departments")
public class ReceptionistDepartmentController {

    private final DepartmentService departmentService;

    public ReceptionistDepartmentController(DepartmentService departmentService) {

        this.departmentService = departmentService;
    }

    @GetMapping("/active")
    public ResponseEntity<List<DepartmentResponse>> getActiveDepartments() {

        return ResponseEntity.ok(departmentService.getActiveDepartments()
        );
    }
}