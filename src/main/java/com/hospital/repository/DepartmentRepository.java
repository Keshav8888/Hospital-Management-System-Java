package com.hospital.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.Department;
import com.hospital.enums.Status;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	Optional<Department> findByName(String name);
	
	boolean existsByName(String name);
	
	Page<Department> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String name, String location, Pageable pageable);
	
	 long countByStatus(Status status);
}
