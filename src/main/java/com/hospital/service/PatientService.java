package com.hospital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hospital.dto.PatientResponse;
import com.hospital.dto.PatientUpdateRequest;
import com.hospital.entity.Patient;
import com.hospital.enums.Status;
import com.hospital.repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    
    public List<PatientResponse> getAllPatients() {

        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
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
    
    public PatientResponse getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found."));

        return convertToResponse(patient);
    }

}