package com.hospital.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.enums.AppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findByPatient(Patient patient);
	
	List<Appointment> findByDoctor(Doctor doctor);
	
	List<Appointment> findByStatus(AppointmentStatus status);
	
	List<Appointment> findByAppointmentDate(LocalDate appointmentDate);
	
	List<Appointment> findByDoctorAndAppointmentDate(Doctor doctor, LocalDate appointmentDate);
	
	List<Appointment> findByPatientAndStatus(Patient patient, AppointmentStatus status);
	
	List<Appointment> findByDoctorAndStatus(Doctor doctor, AppointmentStatus status);
	
	boolean existsByDoctorAndAppointmentDateAndAppointmentTime(
	        Doctor doctor,
	        LocalDate appointmentDate,
	        LocalTime appointmentTime);
}