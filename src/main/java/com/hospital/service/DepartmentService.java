package com.hospital.service;

import org.springframework.stereotype.Service;

import com.hospital.dto.DepartmentRequest;
import com.hospital.dto.DepartmentResponse;
import com.hospital.entity.Department;
import com.hospital.enums.Status;
import com.hospital.repository.DepartmentRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponse createDepartment(DepartmentRequest request) {

        // Check duplicate department name
        if (departmentRepository.existsByName(request.getName())) {
            throw new RuntimeException("Department already exists.");
        }

        // Create Department entity
        Department department = new Department();

        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setLocation(request.getLocation());
        department.setStatus(Status.ACTIVE);

        // Save to database
        Department savedDepartment = departmentRepository.save(department);

        // Convert Entity to Response DTO
        DepartmentResponse response = new DepartmentResponse();

        response.setId(savedDepartment.getId());
        response.setName(savedDepartment.getName());
        response.setDescription(savedDepartment.getDescription());
        response.setLocation(savedDepartment.getLocation());
        response.setStatus(savedDepartment.getStatus());

        return response;
    }
}