package com.hospital.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.hospital.dto.PatientDashboardResponse;
import com.hospital.entity.Patient;
import com.hospital.entity.User;
import com.hospital.enums.AppointmentStatus;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.UserRepository;

@Service
public class PatientDashboardService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    public PatientDashboardService(
            PatientRepository patientRepository,
            UserRepository userRepository,
            AppointmentRepository appointmentRepository) {

        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public PatientDashboardResponse getDashboard(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
        			new RuntimeException("User not found."));

        Patient patient = patientRepository.findByUserId(user.getId()).orElseThrow(() ->
                        new RuntimeException("Patient not found."));

        PatientDashboardResponse response = new PatientDashboardResponse();

        response.setTodaysAppointments(appointmentRepository.countByPatientAndAppointmentDate(patient,LocalDate.now()));

        response.setTotalAppointments(appointmentRepository.countByPatient(patient));

        response.setBookedAppointments(appointmentRepository.countByPatientAndStatus(patient, AppointmentStatus.BOOKED));

        response.setConfirmedAppointments(appointmentRepository.countByPatientAndStatus(patient, AppointmentStatus.CONFIRMED));

        response.setCompletedAppointments(appointmentRepository.countByPatientAndStatus(patient, AppointmentStatus.COMPLETED));

        response.setCancelledAppointments(appointmentRepository.countByPatientAndStatus(patient, AppointmentStatus.CANCELLED));

        return response;
    }
}