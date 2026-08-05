package com.hospital.service;

import org.springframework.stereotype.Service;

import com.hospital.dto.DoctorProfileUpdateRequest;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
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

        if (userRepository.existsByEmail(request.getEmail())) {
            
        	throw new RuntimeException("Email already exists.");
        }

        if (doctorRepository.existsByPhone(request.getPhone())) {
            
        	throw new RuntimeException("Phone number already exists.");
        }

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> 
        						new RuntimeException("Department not found."));

        User user = new User();

        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.DOCTOR);

        user.setStatus(Status.ACTIVE);

        userRepository.save(user);

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
    
//    public List<DoctorResponse> getAllDoctors() {
//
//        List<Doctor> doctors = doctorRepository.findAll();
//
//        List<DoctorResponse> response = new ArrayList<>();
//
//        for (Doctor doctor : doctors) {
//
//            DoctorResponse dto = new DoctorResponse();
//
//            dto.setId(doctor.getId());
//
//            dto.setFirstName(doctor.getFirstName());
//
//            dto.setLastName(doctor.getLastName());
//
//            dto.setDepartment(doctor.getDepartment().getName());
//
//            dto.setSpecialization(doctor.getSpecialization());
//
//            dto.setQualification(doctor.getQualification());
//            
//            dto.setExperience(doctor.getExperience());
//
//            dto.setConsultantionFee(doctor.getConsultantionFee());
//
//            dto.setPhone(doctor.getPhone());
//
//            dto.setEmail(doctor.getUser().getEmail());
//
//            dto.setStatus(doctor.getStatus().name());
//
//            response.add(dto);
//        }
//
//        return response;
//    }
    
    public DoctorResponse getDoctorById(Long id) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> 
        				new RuntimeException("Doctor not found."));

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

        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> 
        				new RuntimeException("Doctor not found."));

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> 
        						new RuntimeException("Department not found."));

        if (!doctor.getPhone().equals(request.getPhone()) && doctorRepository.existsByPhone(request.getPhone())) {

            throw new RuntimeException("Phone number already exists.");
        }

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

        doctorRepository.save(doctor);
    }
    
    @Transactional
    public void deleteDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> 
        				new RuntimeException("Doctor not found."));

        doctor.setStatus(Status.INACTIVE);

        doctorRepository.save(doctor);
    }
    
    @Transactional
    public DoctorResponse getMyProfile(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                    new RuntimeException("User not found."));

        Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(() ->
                        new RuntimeException("Doctor not found."));

        return mapToResponse(doctor);
    }
    
    @Transactional
    public void updateMyProfile(DoctorProfileUpdateRequest request, Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() ->
        			new RuntimeException("User not found."));

        Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(() ->
                        new RuntimeException("Doctor not found."));

        if (!doctor.getPhone().equals(request.getPhone()) && doctorRepository.existsByPhone(request.getPhone())) {

            throw new RuntimeException("Phone number already exists.");
        }

        doctor.setFirstName(request.getFirstName());

        doctor.setLastName(request.getLastName());

        doctor.setGender(request.getGender());

        doctor.setDateOfBirth(request.getDateOfBirth());

        doctor.setPhone(request.getPhone());

        doctor.setQualification(request.getQualification());

        doctor.setSpecialization(request.getSpecialization());

        doctor.setAddress(request.getAddress());

        doctorRepository.save(doctor);
    }
    
//    public List<DoctorResponse> searchDoctors(String keyword) {
//
//        List<Doctor> doctors = doctorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(keyword, keyword, keyword);
//
//        List<DoctorResponse> response = new ArrayList<>();
//
//        for (Doctor doctor : doctors) {
//
//            DoctorResponse dto = new DoctorResponse();
//
//            dto.setId(doctor.getId());
//
//            dto.setFirstName(doctor.getFirstName());
//
//            dto.setLastName(doctor.getLastName());
//
//            dto.setDepartment(doctor.getDepartment().getName());
//
//            dto.setSpecialization(doctor.getSpecialization());
//
//            dto.setQualification(doctor.getQualification());
//
//            dto.setExperience(doctor.getExperience());
//
//            dto.setConsultantionFee(doctor.getConsultantionFee());
//
//            dto.setPhone(doctor.getPhone());
//
//            dto.setEmail(doctor.getUser().getEmail());
//
//            dto.setStatus(doctor.getStatus().name());
//
//            response.add(dto);
//        }
//
//        return response;
//    }
//    
//    public Page<DoctorResponse> getDoctors(int page, int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//
//        Page<Doctor> doctorPage = doctorRepository.findAll(pageable);
//
//        return doctorPage.map(doctor -> {
//
//            DoctorResponse response = new DoctorResponse();
//
//            response.setId(doctor.getId());
//
//            response.setFirstName(doctor.getFirstName());
//
//            response.setLastName(doctor.getLastName());
//
//            response.setDepartment(doctor.getDepartment().getName());
//
//            response.setSpecialization(doctor.getSpecialization());
//
//            response.setQualification(doctor.getQualification());
//
//            response.setExperience(doctor.getExperience());
//
//            response.setConsultantionFee(doctor.getConsultantionFee());
//
//            response.setPhone(doctor.getPhone());
//
//            response.setEmail(doctor.getUser().getEmail());
//
//            response.setStatus(doctor.getStatus().name());
//
//            return response;
//        });
//    }
    
    public Page<DoctorResponse> getDoctors(String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Doctor> doctorPage;

        if (keyword == null || keyword.trim().isEmpty()) {

            doctorPage = doctorRepository.findAll(pageable);

        } else {

            doctorPage = doctorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(keyword, keyword, keyword, pageable);
        }

        return doctorPage.map(doctor -> {

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
        });
    }
    
    private DoctorResponse mapToResponse(Doctor doctor) {

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
}