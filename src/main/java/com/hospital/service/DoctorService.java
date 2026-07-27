package com.hospital.service;

import org.springframework.stereotype.Service;

import com.hospital.dto.DoctorRegisterRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.dto.DoctorUpdateRequest;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.User;
import com.hospital.enums.Role;
import com.hospital.enums.Status;
import com.hospital.repository.DepartmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.transaction.Transactional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorService(DoctorRepository doctorRepository,DepartmentRepository departmentRepository,UserRepository userRepository,PasswordEncoder passwordEncoder) {

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
    
    public List<DoctorResponse> getAllDoctors() {

        List<Doctor> doctors = doctorRepository.findAll();

        List<DoctorResponse> response = new ArrayList<>();

        for (Doctor doctor : doctors) {

            DoctorResponse dto = new DoctorResponse();

            dto.setId(doctor.getId());
            dto.setFirstName(doctor.getFirstName());
            dto.setLastName(doctor.getLastName());
            dto.setDepartment(doctor.getDepartment().getName());
            dto.setSpecialization(doctor.getSpecialization());
            dto.setQualification(doctor.getQualification());
            dto.setExperience(doctor.getExperience());
            dto.setConsultantionFee(doctor.getConsultantionFee());
            dto.setPhone(doctor.getPhone());
            dto.setEmail(doctor.getUser().getEmail());
            dto.setStatus(doctor.getStatus().name());

            response.add(dto);
        }

        return response;
    }
    
    public DoctorResponse getDoctorById(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found."));

        DoctorResponse response = new DoctorResponse();

        response.setId(doctor.getId());
        response.setFirstName(doctor.getFirstName());
        response.setLastName(doctor.getLastName());
        response.setDepartment(doctor.getDepartment().getName());
        response.setSpecialization(doctor.getSpecialization());
        response.setQualification(doctor.getQualification());
        response.setExperience(doctor.getExperience());
        response.setConsultantionFee(doctor.getConsultantionFee());
        response.setPhone(doctor.getPhone());
        response.setEmail(doctor.getUser().getEmail());
        response.setStatus(doctor.getStatus().name());

        return response;
    }
    
    @Transactional
    public void updateDoctor(Long id, DoctorUpdateRequest request) {

        // 1. Find Doctor

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found."));

        // 2. Find Department

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found."));

        // 3. Check if another doctor is using the same phone number

        if (!doctor.getPhone().equals(request.getPhone())
                && doctorRepository.existsByPhone(request.getPhone())) {

            throw new RuntimeException("Phone number already exists.");
        }

        // 4. Update Doctor Details

        doctor.setFirstName(request.getFirstName());

        doctor.setLastName(request.getLastName());

        doctor.setGender(request.getGender());

        doctor.setPhone(request.getPhone());

        doctor.setQualification(request.getQualification());

        doctor.setSpecialization(request.getSpecialization());

        doctor.setExperience(request.getExperience());

        doctor.setConsultantionFee(request.getconsultantion_Fee());

        doctor.setDepartment(department);

        doctor.setAddress(request.getAddress());

        // 5. Save Updated Doctor

        doctorRepository.save(doctor);
    }
    
    @Transactional
    public void deleteDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found."));

        doctor.setStatus(Status.INACTIVE);

        doctorRepository.save(doctor);
    }
}