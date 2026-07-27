package com.hospital.service;

import org.springframework.stereotype.Service;

import com.hospital.dto.DoctorRegisterRequest;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.User;
import com.hospital.enums.Role;
import com.hospital.enums.Status;
import com.hospital.repository.DepartmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.transaction.Transactional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorService(
            DoctorRepository doctorRepository,
            DepartmentRepository departmentRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerDoctor(DoctorRegisterRequest request) {

        // 1. Check Email

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        // 2. Check Phone

        if (doctorRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone number already exists.");
        }

        // 3. Find Department

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found."));

        // 4. Create User

        User user = new User();

        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.DOCTOR);

        user.setStatus(Status.ACTIVE);

        userRepository.save(user);

        // 5. Create Doctor

        Doctor doctor = new Doctor();

        doctor.setUser(user);

        doctor.setDepartment(department);

        doctor.setFirstName(request.getFirstName());

        doctor.setLastName(request.getLastName());

        doctor.setGender(request.getGender());

        doctor.setDateOfBirth(request.getDateOfBirth());

        doctor.setPhone(request.getPhone());

        doctor.setQualification(request.getQualification());

        doctor.setSpecialization(request.getSpecialization());

        doctor.setExperience(request.getExperience());

        doctor.setConsultantionFee(request.getConsultationFee());

        doctor.setAddress(request.getAddress());

        doctor.setStatus(Status.ACTIVE);

        doctorRepository.save(doctor);
    }
}