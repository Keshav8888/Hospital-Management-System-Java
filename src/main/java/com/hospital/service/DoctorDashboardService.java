package com.hospital.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.hospital.dto.DoctorDashboardResponse;
import com.hospital.entity.Doctor;
import com.hospital.entity.User;
import com.hospital.enums.AppointmentStatus;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.UserRepository;

@Service
public class DoctorDashboardService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorDashboardService(DoctorRepository doctorRepository, UserRepository userRepository, AppointmentRepository appointmentRepository) {

        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public DoctorDashboardResponse getDashboard(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                    new RuntimeException("User not found."));

        Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(() ->
                        new RuntimeException("Doctor not found."));

        DoctorDashboardResponse response = new DoctorDashboardResponse();

        response.setTodaysAppointments(appointmentRepository.countByDoctorAndAppointmentDate(doctor, LocalDate.now()));

        response.setTotalAppointments(appointmentRepository.countByDoctor(doctor));

        response.setCompletedAppointments(appointmentRepository.countByDoctorAndStatus(doctor, AppointmentStatus.COMPLETED));

        response.setBookedAppointments(appointmentRepository.countByDoctorAndStatus(doctor, AppointmentStatus.BOOKED));

        response.setConfirmedAppointments(appointmentRepository.countByDoctorAndStatus(doctor, AppointmentStatus.CONFIRMED));

        response.setCancelledAppointments(appointmentRepository.countByDoctorAndStatus(doctor, AppointmentStatus.CANCELLED));

        return response;
    }
}