package com.hospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.Doctor;
import com.hospital.entity.Department;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByPhone(String phone);

    List<Doctor> findByDepartment(Department department);

    List<Doctor> findBySpecialization(String specialization);

    Optional<Doctor> findByUserId(Long userId);
    
    long countByStatus(com.hospital.enums.Status status);
}