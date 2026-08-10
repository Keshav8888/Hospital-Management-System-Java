package com.hospital.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.hospital.dto.ReceptionistDashboardResponse;
import com.hospital.enums.AppointmentStatus;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.PatientRepository;

@Service
public class ReceptionistDashboardService {

    private final AppointmentRepository appointmentRepository;

    private final PatientRepository patientRepository;

    public ReceptionistDashboardService(AppointmentRepository appointmentRepository, PatientRepository patientRepository) {

        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    public ReceptionistDashboardResponse getDashboard() {

        ReceptionistDashboardResponse response = new ReceptionistDashboardResponse();

        response.setTodaysAppointments(appointmentRepository.countByAppointmentDate(LocalDate.now()));

        response.setTotalAppointments(appointmentRepository.count());

        response.setBookedAppointments(appointmentRepository.countByStatus(AppointmentStatus.BOOKED));

        response.setConfirmedAppointments(appointmentRepository.countByStatus(AppointmentStatus.CONFIRMED));

        response.setCompletedAppointments(appointmentRepository.countByStatus(AppointmentStatus.COMPLETED));

        response.setCancelledAppointments(appointmentRepository.countByStatus(AppointmentStatus.CANCELLED));

        response.setTotalPatients(patientRepository.count());

        return response;
    }
}