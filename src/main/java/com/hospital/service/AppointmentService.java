package com.hospital.service;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.dto.AppointmentBookingRequest;
import com.hospital.dto.AppointmentResponse;
import com.hospital.entity.Appointment;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.entity.User;
import com.hospital.enums.AppointmentStatus;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DepartmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.UserRepository;

@Service
public class AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final PatientRepository patientRepository;	
	private final DoctorRepository doctorRepository;	
	private final DepartmentRepository departmentRepository;
	private final UserRepository userRepository;
	
	public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, DepartmentRepository departmentRepository, UserRepository userRepository) {

	    this.appointmentRepository = appointmentRepository;
	    this.patientRepository = patientRepository;
	    this.doctorRepository = doctorRepository;
	    this.departmentRepository = departmentRepository;
	    this.userRepository = userRepository;
	}
	
	@Transactional
	public AppointmentResponse bookAppointment(AppointmentBookingRequest request, Authentication authentication) {
		
		String email = authentication.getName();
		
		User user = userRepository.findByEmail(email)
		        .orElseThrow(() ->
		                new RuntimeException("User not found."));
		
		Patient patient = patientRepository
		        .findByUserId(user.getId())
		        .orElseThrow(() ->
		                new RuntimeException("Patient not found."));
		
		Doctor doctor = doctorRepository
		        .findById(request.getDoctorId())
		        .orElseThrow(() ->
		                new RuntimeException("Doctor not found."));
		
		Department department = departmentRepository
		        .findById(request.getDepartmentId())
		        .orElseThrow(() ->
		                new RuntimeException("Department not found."));
		
		if (!doctor.getDepartment().getId()
		        .equals(department.getId())) {

		    throw new RuntimeException(
		            "Selected doctor does not belong to the selected department.");
		}
		
		if (appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTime(
		        doctor,
		        request.getAppointmentDate(),
		        request.getAppointmentTime())) {

		    throw new RuntimeException(
		            "Doctor already has an appointment at the selected date and time.");
		}
		
		String appointmentNumber = "APT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		
		Appointment appointment = new Appointment();
		appointment.setAppointmentNumber(appointmentNumber);
		appointment.setPatient(patient);
		appointment.setDoctor(doctor);
		appointment.setDepartment(department);
		appointment.setAppointmentDate(request.getAppointmentDate());
		appointment.setAppointmentTime(request.getAppointmentTime());
		appointment.setReason(request.getReason());
		appointment.setSymptoms(request.getSymptoms());
		appointment.setStatus(AppointmentStatus.BOOKED);
		Appointment savedAppointment = appointmentRepository.save(appointment);
		AppointmentResponse response = new AppointmentResponse();
		response.setId(savedAppointment.getId());

		response.setAppointmentNumber(savedAppointment.getAppointmentNumber());

		response.setPatientId(patient.getId());

		response.setPatientName(patient.getFirstName() + " " + patient.getLastName());

		response.setDoctorId(doctor.getId());

		response.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());

		response.setDepartmentId(department.getId());

		response.setDepartmentName(department.getName());

		response.setAppointmentDate(savedAppointment.getAppointmentDate());

		response.setAppointmentTime(savedAppointment.getAppointmentTime());

		response.setReason(savedAppointment.getReason());

		response.setSymptoms(savedAppointment.getSymptoms());

		response.setStatus(savedAppointment.getStatus());

		response.setRemarks(savedAppointment.getRemarks());

		response.setCreatedAt(savedAppointment.getCreatedAt());
		
		return response;
	}
}