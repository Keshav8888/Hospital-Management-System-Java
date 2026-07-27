package com.hospital.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.DepartmentRequest;
import com.hospital.dto.DepartmentResponse;
import com.hospital.service.DepartmentService;

@RestController
@RequestMapping("/api/admin/departments")
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@PostMapping
	public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody DepartmentRequest request){
		DepartmentResponse response=departmentService.createDepartment(request);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
}
