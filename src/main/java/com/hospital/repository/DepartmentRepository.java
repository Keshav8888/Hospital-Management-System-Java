package com.hospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	Optional<Department> findByName(String name);
	
	boolean existsByName(String name);
	
}
