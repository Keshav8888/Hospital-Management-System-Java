package com.hospital.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.DepartmentRequest;
import com.hospital.dto.DepartmentResponse;
import com.hospital.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/departments")
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		
		this.departmentService = departmentService;
	}
	
	@PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse response = departmentService.createDepartment(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
	
//	@GetMapping
//    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
//
//        return ResponseEntity.ok(departmentService.getAllDepartments());
//    }
	
	@GetMapping
	public ResponseEntity<Page<DepartmentResponse>> getDepartments(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

	    return ResponseEntity.ok(departmentService.getDepartments(keyword, page, size));
	}

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id) {

        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request) {

        departmentService.updateDepartment(id, request);

        return ResponseEntity.ok("Department updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.ok("Department deactivated successfully.");
    }
	
}
