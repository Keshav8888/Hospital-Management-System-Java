package com.hospital.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        if (departmentRepository.existsByName(request.getName())) {
            
        	throw new RuntimeException("Department already exists.");
        }

        Department department = new Department();

        department.setName(request.getName());

        department.setDescription(request.getDescription());

        department.setLocation(request.getLocation());

        department.setStatus(Status.ACTIVE);

        Department savedDepartment = departmentRepository.save(department);

        return convertToResponse(savedDepartment);
    }
    
//    public List<DepartmentResponse> getAllDepartments() {
//
//        List<Department> departments = departmentRepository.findAll();
//
//        return departments.stream().map(this::convertToResponse).toList();
//    }
    
    public Page<DepartmentResponse> getDepartments(String keyword, int page, int size,  String sortBy, String sortDir) {

    	Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, sortBy);
    	
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Department> departmentPage;

        if (keyword == null || keyword.trim().isEmpty()) {

            departmentPage = departmentRepository.findAll(pageable);

        } else {

            departmentPage = departmentRepository.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword, keyword, pageable);
        }

        return departmentPage.map(this::convertToResponse);
    }
    
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id).orElseThrow(() -> 
        						new RuntimeException("Department not found."));

        return convertToResponse(department);
    }
    
    public void updateDepartment(Long id, DepartmentRequest request) {

        Department department = departmentRepository.findById(id).orElseThrow(() -> 
        						new RuntimeException("Department not found."));

        if (!department.getName().equalsIgnoreCase(request.getName()) && departmentRepository.existsByName(request.getName())) {

            throw new RuntimeException("Department already exists.");
        }

        department.setName(request.getName());

        department.setDescription(request.getDescription());

        department.setLocation(request.getLocation());

        departmentRepository.save(department);
    }
    
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id).orElseThrow(() -> 
        						new RuntimeException("Department not found."));

        department.setStatus(Status.INACTIVE);

        departmentRepository.save(department);
    }
    
    public List<DepartmentResponse> getActiveDepartments() {

        return departmentRepository.findByStatus(Status.ACTIVE)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }
    
    private DepartmentResponse convertToResponse(Department department) {

        DepartmentResponse response = new DepartmentResponse();

        response.setId(department.getId());

        response.setName(department.getName());

        response.setDescription(department.getDescription());

        response.setLocation(department.getLocation());

        response.setStatus(department.getStatus());

        return response;
    }

}