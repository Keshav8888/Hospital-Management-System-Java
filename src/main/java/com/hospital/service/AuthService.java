package com.hospital.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.dto.AdminRegisterRequest;
import com.hospital.dto.AuthResponse;
import com.hospital.dto.LoginRequest;
import com.hospital.dto.PatientRegisterRequest;
import com.hospital.entity.Admin;
import com.hospital.entity.User;
import com.hospital.enums.Role;
import com.hospital.enums.Status;
import com.hospital.repository.AdminRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.UserRepository;
import com.hospital.util.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

	private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientService patientService;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PatientRepository patientRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder, PatientService patientService,  JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.patientService = patientService;
        this.jwtUtil = jwtUtil;
    }
    
    @Transactional
    public void registerPatient(PatientRegisterRequest request) {

    	 patientService.registerPatient(request);
    
	}
    
    @Transactional
    public void registerAdmin(AdminRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        if (adminRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone number already exists.");
        }

        User user = new User();

        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.ADMIN);

        user.setStatus(Status.ACTIVE);

        userRepository.save(user);

        Admin admin = new Admin();

        admin.setUser(user);

        admin.setFirstName(request.getFirstName());

        admin.setLastName(request.getLastName());

        admin.setPhone(request.getPhone());

        admin.setStatus(Status.ACTIVE);

        adminRepository.save(admin);
    }
    
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password.");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name()
        );
 
    }
}
