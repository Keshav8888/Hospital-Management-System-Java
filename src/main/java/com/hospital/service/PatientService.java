package com.hospital.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.dto.PatientRegisterRequest;
import com.hospital.dto.PatientResponse;
import com.hospital.dto.PatientUpdateRequest;
import com.hospital.entity.Patient;
import com.hospital.entity.User;
import com.hospital.enums.Role;
import com.hospital.enums.Status;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PatientService {

	private final PatientRepository patientRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public PatientService(PatientRepository patientRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {

	    this.patientRepository = patientRepository;
	    this.userRepository = userRepository;
	    this.passwordEncoder = passwordEncoder;
	}
    
	@Transactional
	public void registerPatient(PatientRegisterRequest request) {

	    if (userRepository.existsByEmail(request.getEmail())) {
	        throw new RuntimeException("Email already exists.");
	    }

	    if (patientRepository.existsByPhone(request.getPhone())) {
	        throw new RuntimeException("Phone number already exists.");
	    }

	    User user = new User();

	    user.setEmail(request.getEmail());

	    user.setPassword(passwordEncoder.encode(request.getPassword()));

	    user.setRole(Role.PATIENT);

	    user.setStatus(Status.ACTIVE);

	    userRepository.save(user);

	    Patient patient = new Patient();

	    patient.setUser(user);

	    patient.setFirstName(request.getFirstName());

	    patient.setLastName(request.getLastName());

	    patient.setGender(request.getGender());

	    patient.setDateOfBirth(request.getDateOfBirth());

	    patient.setPhone(request.getPhone());

	    patient.setBloodGroup(request.getBloodGroup());

	    patient.setAddress(request.getAddress());

	    patient.setAllergies(request.getAllergies());

	    patient.setMedicalHistory(request.getMedicalHistory());

	    patient.setStatus(Status.ACTIVE);

	    patientRepository.save(patient);
	}
	
	@Transactional
	public void registerPatientByReceptionist(PatientRegisterRequest request) {

	    registerPatient(request);
	}
	
//    public List<PatientResponse> getAllPatients() {
//
//        List<Patient> patients = patientRepository.findAll();
//
//        return patients.stream().map(this::convertToResponse).collect(Collectors.toList());
//    }
//    
//    public List<PatientResponse> searchPatients(String keyword) {
//
//        List<Patient> patients = patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneContainingOrUserEmailContainingIgnoreCase(keyword, keyword, keyword, keyword);
//
//        return patients.stream().map(this::convertToResponse).collect(Collectors.toList());
//    }
	
	public Page<PatientResponse> getPatients(String keyword, int page, int size) {

	    Pageable pageable = PageRequest.of(page, size);

	    Page<Patient> patientPage;

	    if (keyword == null || keyword.trim().isEmpty()) {

	        patientPage = patientRepository.findAll(pageable);

	    } else {

	        patientPage = patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrUserEmailContainingIgnoreCase(keyword, keyword, keyword, keyword, pageable);
	    }

	    return patientPage.map(this::convertToResponse);
	}
    
    
    public PatientResponse getPatientById(Long id) {

        Patient patient = patientRepository.findById(id).orElseThrow(() -> 
        				new RuntimeException("Patient not found."));

        return convertToResponse(patient);
    }
    
    @Transactional
    public void updatePatient(Long id, PatientUpdateRequest request) {

        Patient patient = patientRepository.findById(id).orElseThrow(() -> 
        				new RuntimeException("Patient not found."));

        if (!patient.getPhone().equals(request.getPhone()) && patientRepository.existsByPhone(request.getPhone())) {

            throw new RuntimeException("Phone number already exists.");
        }

        patient.setFirstName(request.getFirstName());

        patient.setLastName(request.getLastName());

        patient.setGender(request.getGender());

        patient.setDateOfBirth(request.getDateOfBirth());

        patient.setPhone(request.getPhone());

        patient.setBloodGroup(request.getBloodGroup());

        patient.setAddress(request.getAddress());

        patient.setAllergies(request.getAllergies());

        patient.setMedicalHistory(request.getMedicalHistory());

        patientRepository.save(patient);
    }
    
    @Transactional
    public void deletePatient(Long id) {

        Patient patient = patientRepository.findById(id).orElseThrow(() -> 
        				new RuntimeException("Patient not found."));

        patient.setStatus(Status.INACTIVE);

        patientRepository.save(patient);
    }
    
    private PatientResponse convertToResponse(Patient patient) {

        PatientResponse response = new PatientResponse();

        response.setId(patient.getId());

        response.setFirstName(patient.getFirstName());

        response.setLastName(patient.getLastName());

        response.setEmail(patient.getUser().getEmail());

        response.setGender(patient.getGender().name());

        response.setDateOfBirth(patient.getDateOfBirth());

        response.setPhone(patient.getPhone());

        response.setBloodGroup(patient.getBloodGroup());

        response.setAddress(patient.getAddress());

        response.setAllergies(patient.getAllergies());

        response.setMedicalHistory(patient.getMedicalHistory());

        response.setStatus(patient.getStatus().name());

        return response;
    }

}