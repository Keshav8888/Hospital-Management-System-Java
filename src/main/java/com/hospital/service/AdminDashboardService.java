package com.hospital.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.hospital.dto.AdminDashboardResponse;
import com.hospital.enums.AppointmentStatus;
import com.hospital.enums.Status;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DepartmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.ReceptionistRepository;

@Service
public class AdminDashboardService {

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    private final ReceptionistRepository receptionistRepository;

    private final DepartmentRepository departmentRepository;

    private final AppointmentRepository appointmentRepository;

    public AdminDashboardService(DoctorRepository doctorRepository, PatientRepository patientRepository, ReceptionistRepository receptionistRepository, DepartmentRepository departmentRepository, AppointmentRepository appointmentRepository) {

        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.receptionistRepository = receptionistRepository;
        this.departmentRepository = departmentRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public AdminDashboardResponse getDashboard() {

        AdminDashboardResponse response = new AdminDashboardResponse();

        response.setTotalDoctors(doctorRepository.countByStatus(Status.ACTIVE));

        response.setTotalPatients(patientRepository.countByStatus(Status.ACTIVE));

        response.setTotalReceptionists(receptionistRepository.countByStatus(Status.ACTIVE));

        response.setTotalDepartments(departmentRepository.count());

        response.setTotalAppointments(appointmentRepository.count());

        response.setTodaysAppointments(appointmentRepository.countByAppointmentDate(LocalDate.now()));

        response.setCompletedAppointments(appointmentRepository.countByStatus(AppointmentStatus.COMPLETED));

        response.setCancelledAppointments(appointmentRepository.countByStatus(AppointmentStatus.CANCELLED));

        return response;
    }
}