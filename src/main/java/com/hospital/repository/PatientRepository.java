package com.hospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	Optional<Patient> findByUserId(Long userId);

    boolean existsByPhone(String phone);
    
    List<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneContainingOrUserEmailContainingIgnoreCase(String firstName, String lastName, String phone, String email);
}
