package com.hospital.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.Patient;
import com.hospital.enums.Status;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	Optional<Patient> findByUserId(Long userId);

    boolean existsByPhone(String phone);
    
//    List<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneContainingOrUserEmailContainingIgnoreCase(String firstName, String lastName, String phone, String email);

    long countByStatus(Status status);
    
    Page<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrUserEmailContainingIgnoreCase(String firstName, String lastName, String phone, String email, Pageable pageable);
    
}
