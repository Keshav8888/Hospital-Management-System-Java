package com.hospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.Receptionist;
import com.hospital.enums.Status;

public interface ReceptionistRepository extends JpaRepository<Receptionist, Long> {

	Optional<Receptionist> findByUserId(Long userId);

    boolean existsByPhone(String phone);
    
    List<Receptionist> findByStatus(Status status);
}
