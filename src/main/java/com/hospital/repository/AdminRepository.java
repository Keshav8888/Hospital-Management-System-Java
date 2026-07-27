package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByPhone(String phone);

}