package com.hospital.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.dto.AppointmentBookingRequest;
import com.hospital.dto.AppointmentByReceptionistRequest;
import com.hospital.dto.AppointmentRemarksRequest;
import com.hospital.dto.AppointmentRescheduleRequest;
import com.hospital.dto.AppointmentResponse;
import com.hospital.entity.Appointment;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.entity.User;
import com.hospital.enums.AppointmentStatus;
import com.hospital.enums.Status;
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
		
		User user = userRepository.findByEmail(email).orElseThrow(() ->
		                new RuntimeException("User not found."));
		
		Patient patient = patientRepository.findByUserId(user.getId()).orElseThrow(() ->
		                new RuntimeException("Patient not found."));
		
		Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(() ->
		                new RuntimeException("Doctor not found."));
		
		Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() ->
		                new RuntimeException("Department not found."));
		
		if (!doctor.getDepartment().getId().equals(department.getId())) {

		    throw new RuntimeException("Selected doctor does not belong to the selected department.");
		}
		
		if (appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTime(doctor, request.getAppointmentDate(), request.getAppointmentTime())) {

		    throw new RuntimeException("Doctor already has an appointment at the selected date and time.");
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
		
		return convertToResponse(savedAppointment);
	}
	
	@Transactional
	public AppointmentResponse bookAppointmentByReceptionist(AppointmentByReceptionistRequest request) {

	    Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() ->
	                    new RuntimeException("Patient not found."));

	    if (patient.getStatus() != Status.ACTIVE) {
	        
	    	throw new RuntimeException("Patient is inactive.");
	    }
	    
	    Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(() ->
	                    new RuntimeException("Doctor not found."));

	    if (doctor.getStatus() != Status.ACTIVE) {
	        
	    	throw new RuntimeException("Doctor is inactive.");
	    }
	    
	    Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() ->
	                    new RuntimeException("Department not found."));
	    
	    if (!doctor.getDepartment().getId().equals(department.getId())) {

	        throw new RuntimeException("Selected doctor does not belong to the selected department.");
	    }
	    
	    if (appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTime(doctor, request.getAppointmentDate(), request.getAppointmentTime())) {

	        throw new RuntimeException("Doctor already has an appointment at the selected date and time.");
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
	    
	    appointment.setCreatedAt(LocalDateTime.now());
	    
	    Appointment savedAppointment = appointmentRepository.save(appointment);
	    
	    return convertToResponse(savedAppointment);
	}
	
	public Page<AppointmentResponse> getAppointments(String keyword, int page, int size) {

	    Pageable pageable = PageRequest.of(page, size);

	    Page<Appointment> appointmentPage;

	    if (keyword == null || keyword.trim().isEmpty()) {

	        appointmentPage = appointmentRepository.findAll(pageable);

	    } else {

	        appointmentPage = appointmentRepository.findByAppointmentNumberContainingIgnoreCaseOrPatientFirstNameContainingIgnoreCaseOrPatientLastNameContainingIgnoreCaseOrDoctorFirstNameContainingIgnoreCaseOrDoctorLastNameContainingIgnoreCase(keyword, keyword, keyword, keyword, keyword, pageable);
	    }

	    return appointmentPage.map(this::convertToResponse);
	}
	
	public List<AppointmentResponse> getTodaysAppointments() {

	    List<Appointment> appointments = appointmentRepository.findByAppointmentDateOrderByAppointmentTimeAsc(LocalDate.now());

	    return appointments.stream().map(this::convertToResponse).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<AppointmentResponse> getAllAppointments() {

	    List<Appointment> appointments = appointmentRepository.findAllByOrderByAppointmentDateAscAppointmentTimeAsc();

	    return appointments.stream().map(this::convertToResponse).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<AppointmentResponse> getMyAppointments(Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElseThrow(() ->
	                    new RuntimeException("User not found."));

	    Patient patient = patientRepository.findByUserId(user.getId()).orElseThrow(() ->
	                    new RuntimeException("Patient not found."));

	    List<Appointment> appointments = appointmentRepository.findByPatient(patient);

	    List<AppointmentResponse> responses = new ArrayList<>();

	    for (Appointment appointment : appointments) {

	        AppointmentResponse response = new AppointmentResponse();

	        response.setId(appointment.getId());

	        response.setAppointmentNumber(appointment.getAppointmentNumber());

	        response.setPatientId(patient.getId());

	        response.setPatientName(patient.getFirstName() + " " + patient.getLastName());

	        response.setDoctorId(appointment.getDoctor().getId());

	        response.setDoctorName(appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());

	        response.setDepartmentId(appointment.getDepartment().getId());

	        response.setDepartmentName(appointment.getDepartment().getName());

	        response.setAppointmentDate(appointment.getAppointmentDate());

	        response.setAppointmentTime(appointment.getAppointmentTime());

	        response.setReason(appointment.getReason());

	        response.setSymptoms(appointment.getSymptoms());

	        response.setStatus(appointment.getStatus());

	        response.setRemarks(appointment.getRemarks());

	        response.setCreatedAt(appointment.getCreatedAt());

	        responses.add(response);
	    }

	    return responses;
	}
	
	@Transactional(readOnly = true)
	public AppointmentResponse getMyAppointmentById(Long appointmentId, Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElseThrow(() ->
	                    new RuntimeException("User not found."));

	    Patient patient = patientRepository.findByUserId(user.getId()).orElseThrow(() ->
	                    new RuntimeException("Patient not found."));

	    Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
	                    new RuntimeException("Appointment not found."));
	    
	    if (!appointment.getPatient().getId().equals(patient.getId())) {

	        throw new RuntimeException("You are not authorized to view this appointment.");
	    }
	    
	    AppointmentResponse response = new AppointmentResponse();

	    response.setId(appointment.getId());

	    response.setAppointmentNumber(appointment.getAppointmentNumber());

	    response.setPatientId(patient.getId());

	    response.setPatientName(patient.getFirstName() + " " + patient.getLastName());

	    response.setDoctorId(appointment.getDoctor().getId());

	    response.setDoctorName(appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());

	    response.setDepartmentId(appointment.getDepartment().getId());

	    response.setDepartmentName(appointment.getDepartment().getName());

	    response.setAppointmentDate(appointment.getAppointmentDate());

	    response.setAppointmentTime(appointment.getAppointmentTime());

	    response.setReason(appointment.getReason());

	    response.setSymptoms(appointment.getSymptoms());

	    response.setStatus(appointment.getStatus());

	    response.setRemarks(appointment.getRemarks());

	    response.setCreatedAt(appointment.getCreatedAt());

	    return response;
	}
	
	@Transactional
	public void cancelAppointment(Long appointmentId, Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElseThrow(() ->
	                    new RuntimeException("User not found."));

	    Patient patient = patientRepository.findByUserId(user.getId()).orElseThrow(() ->
	                    new RuntimeException("Patient not found."));

	    Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
	                    new RuntimeException("Appointment not found."));
	    
	    if (!appointment.getPatient().getId().equals(patient.getId())) {

	        throw new RuntimeException("You are not authorized to cancel this appointment.");
	    }
	    
	    if (appointment.getStatus() != AppointmentStatus.BOOKED) {

	        throw new RuntimeException("Only booked appointments can be cancelled.");
	    }
	    
	    appointment.setStatus(AppointmentStatus.CANCELLED);
	    
	    appointmentRepository.save(appointment);
	    
	}
	
	@Transactional(readOnly = true)
	public List<AppointmentResponse> getTodayAppointments(Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElseThrow(() ->
	                    new RuntimeException("User not found."));
	    
	    Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(() ->
	                    new RuntimeException("Doctor not found."));
	    
	    LocalDate today = LocalDate.now();
	    
	    List<Appointment> appointments = appointmentRepository.findByDoctorAndAppointmentDate(doctor,today);
	    
	    List<AppointmentResponse> responses = new ArrayList<>();
	    
	    for (Appointment appointment : appointments) {

	        AppointmentResponse response = new AppointmentResponse();

	        response.setId(appointment.getId());

	        response.setAppointmentNumber(appointment.getAppointmentNumber());

	        response.setPatientId(appointment.getPatient().getId());

	        response.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());

	        response.setDoctorId(doctor.getId());

	        response.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());

	        response.setDepartmentId(appointment.getDepartment().getId());

	        response.setDepartmentName(appointment.getDepartment().getName());

	        response.setAppointmentDate(appointment.getAppointmentDate());

	        response.setAppointmentTime(appointment.getAppointmentTime());

	        response.setReason(appointment.getReason());

	        response.setSymptoms(appointment.getSymptoms());

	        response.setStatus(appointment.getStatus());

	        response.setRemarks(appointment.getRemarks());

	        response.setCreatedAt(appointment.getCreatedAt());

	        responses.add(response);
	    }
	    
	    return responses;
	}
	
	@Transactional(readOnly = true)
	public List<AppointmentResponse> getMyAppointmentsAsDoctor(Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElseThrow(() ->
	                    new RuntimeException("User not found."));

	    Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(() ->
	                    new RuntimeException("Doctor not found."));

	    List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);

	    List<AppointmentResponse> responses = new ArrayList<>();

	    for (Appointment appointment : appointments) {

	        AppointmentResponse response = new AppointmentResponse();

	        response.setId(appointment.getId());

	        response.setAppointmentNumber(appointment.getAppointmentNumber());

	        response.setPatientId(appointment.getPatient().getId());

	        response.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());

	        response.setDoctorId(doctor.getId());

	        response.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());

	        response.setDepartmentId(appointment.getDepartment().getId());

	        response.setDepartmentName(appointment.getDepartment().getName());

	        response.setAppointmentDate(appointment.getAppointmentDate());

	        response.setAppointmentTime(appointment.getAppointmentTime());

	        response.setReason(appointment.getReason());

	        response.setSymptoms(appointment.getSymptoms());

	        response.setStatus(appointment.getStatus());

	        response.setRemarks(appointment.getRemarks());

	        response.setCreatedAt(appointment.getCreatedAt());

	        responses.add(response);
	    }

	    return responses;
	}
	
	@Transactional
	public void confirmAppointment(Long appointmentId, Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElseThrow(() ->
	                    new RuntimeException("User not found."));

	    Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(() ->
	                    new RuntimeException("Doctor not found."));

	    Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
	                    new RuntimeException("Appointment not found."));
	    
	    if (!appointment.getDoctor().getId().equals(doctor.getId())) {

	        throw new RuntimeException("You are not authorized to confirm this appointment.");
	    }
	    
	    if (appointment.getStatus() != AppointmentStatus.BOOKED) {

	        throw new RuntimeException("Only booked appointments can be confirmed.");
	    }
	    
	    appointment.setStatus(AppointmentStatus.CONFIRMED);
	    
	    appointmentRepository.save(appointment);
	    
	}
	
	@Transactional(readOnly = true)
	public AppointmentResponse getAppointmentDetailsForDoctor(Long appointmentId,Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElseThrow(() ->
	                    new RuntimeException("User not found."));

	    Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(() ->
	                    new RuntimeException("Doctor not found."));

	    Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
	                    new RuntimeException("Appointment not found."));

	    if (!appointment.getDoctor().getId().equals(doctor.getId())) {

	        throw new RuntimeException("You are not authorized to view this appointment.");
	    }

	    AppointmentResponse response = new AppointmentResponse();

	    response.setId(appointment.getId());

	    response.setAppointmentNumber(appointment.getAppointmentNumber());

	    response.setPatientId(appointment.getPatient().getId());

	    response.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());

	    response.setDoctorId(doctor.getId());

	    response.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());

	    response.setDepartmentId(appointment.getDepartment().getId());

	    response.setDepartmentName(appointment.getDepartment().getName());

	    response.setAppointmentDate(appointment.getAppointmentDate());

	    response.setAppointmentTime(appointment.getAppointmentTime());

	    response.setReason(appointment.getReason());

	    response.setSymptoms(appointment.getSymptoms());

	    response.setStatus(appointment.getStatus());

	    response.setRemarks(appointment.getRemarks());

	    response.setCreatedAt(appointment.getCreatedAt());

	    return response;
	}
	
	@Transactional
	public void completeAppointment(Long appointmentId, Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElseThrow(() ->
	                    new RuntimeException("User not found."));

	    Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(() ->
	                    new RuntimeException("Doctor not found."));

	    Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
	                    new RuntimeException("Appointment not found."));

	    if (!appointment.getDoctor().getId().equals(doctor.getId())) {

	        throw new RuntimeException("You are not authorized to complete this appointment.");
	    }

	    if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {

	        throw new RuntimeException("Only confirmed appointments can be completed.");
	    }

	    appointment.setStatus(AppointmentStatus.COMPLETED);

	    appointmentRepository.save(appointment);
	}
	
	@Transactional
	public void addConsultationRemarks(Long appointmentId, AppointmentRemarksRequest request, Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElseThrow(() ->
	                    new RuntimeException("User not found."));

	    Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(() ->
	                    new RuntimeException("Doctor not found."));

	    Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
	                    new RuntimeException("Appointment not found."));

	    if (!appointment.getDoctor().getId().equals(doctor.getId())) {

	        throw new RuntimeException("You are not authorized to update this appointment.");
	    }

	    if (appointment.getStatus() != AppointmentStatus.COMPLETED) {

	        throw new RuntimeException("Remarks can only be added after the appointment is completed.");
	    }

	    appointment.setRemarks(request.getRemarks());

	    appointmentRepository.save(appointment);
	}
	
	@Transactional(readOnly = true)
	public List<AppointmentResponse> getAppointmentHistory(Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElseThrow(() ->
	                    new RuntimeException("User not found."));

	    Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(() ->
	                    new RuntimeException("Doctor not found."));

	    List<Appointment> appointments = appointmentRepository.findByDoctorAndStatus(doctor, AppointmentStatus.COMPLETED);

	    List<AppointmentResponse> responses = new ArrayList<>();

	    for (Appointment appointment : appointments) {

	        AppointmentResponse response = new AppointmentResponse();

	        response.setId(appointment.getId());

	        response.setAppointmentNumber(appointment.getAppointmentNumber());

	        response.setPatientId(appointment.getPatient().getId());

	        response.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());

	        response.setDoctorId(doctor.getId());

	        response.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());

	        response.setDepartmentId(appointment.getDepartment().getId());

	        response.setDepartmentName(appointment.getDepartment().getName());

	        response.setAppointmentDate(appointment.getAppointmentDate());

	        response.setAppointmentTime(appointment.getAppointmentTime());

	        response.setReason(appointment.getReason());

	        response.setSymptoms(appointment.getSymptoms());

	        response.setStatus(appointment.getStatus());

	        response.setRemarks(appointment.getRemarks());

	        response.setCreatedAt(appointment.getCreatedAt());

	        responses.add(response);
	    }

	    return responses;
	}
	
//	@Transactional(readOnly = true)
//	public Page<AppointmentResponse> searchAppointments(
//	        String keyword, int page, int size) {
//
//	    Pageable pageable = PageRequest.of(page, size);
//
//	    Page<Appointment> appointments = appointmentRepository.findByAppointmentNumberContainingIgnoreCaseOrPatientFirstNameContainingIgnoreCaseOrPatientLastNameContainingIgnoreCaseOrDoctorFirstNameContainingIgnoreCaseOrDoctorLastNameContainingIgnoreCase(keyword, keyword, keyword, keyword, keyword, pageable);
//
//	    return appointments.map(this::convertToResponse);
//	}
	
	@Transactional
	public void cancelAppointment(Long appointmentId) {

	    Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
	                    new RuntimeException("Appointment not found."));

	    if (appointment.getStatus() == AppointmentStatus.CANCELLED) {

	        throw new RuntimeException("Appointment is already cancelled.");
	    }

	    if (appointment.getStatus() == AppointmentStatus.COMPLETED) {

	        throw new RuntimeException("Completed appointment cannot be cancelled.");
	    }

	    appointment.setStatus(AppointmentStatus.CANCELLED);

	    appointmentRepository.save(appointment);
	}
	
	@Transactional
	public AppointmentResponse rescheduleAppointment(Long appointmentId, AppointmentRescheduleRequest request) {

	    Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
	                    new RuntimeException("Appointment not found."));

	    if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
	        
	    	throw new RuntimeException("Cancelled appointment cannot be rescheduled.");
	    }

	    if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
	        
	    	throw new RuntimeException("Completed appointment cannot be rescheduled.");
	    }

	    Doctor doctor = appointment.getDoctor();

	    boolean exists = appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTimeAndIdNot(doctor, request.getAppointmentDate(), request.getAppointmentTime(), appointmentId);

	    if (exists) {
	        
	    	throw new RuntimeException("Doctor already has another appointment at the selected date and time.");
	    }

	    appointment.setAppointmentDate(request.getAppointmentDate());

	    appointment.setAppointmentTime(request.getAppointmentTime());

	    Appointment updatedAppointment = appointmentRepository.save(appointment);

	    return convertToResponse(updatedAppointment);
	}
	
	@Transactional(readOnly = true)
	public AppointmentResponse getAppointmentDetailsForAdmin(Long appointmentId) {

	    Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
	                    		new RuntimeException("Appointment not found."));

	    return convertToResponse(appointment);
	}
	
	private AppointmentResponse convertToResponse(Appointment appointment) {

	    AppointmentResponse response = new AppointmentResponse();

	    response.setId(appointment.getId());

	    response.setAppointmentNumber(appointment.getAppointmentNumber());

	    response.setPatientId(appointment.getPatient().getId());

	    response.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());

	    response.setDoctorId(appointment.getDoctor().getId());

	    response.setDoctorName(appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());

	    response.setDepartmentId(appointment.getDepartment().getId());

	    response.setDepartmentName(appointment.getDepartment().getName());

	    response.setAppointmentDate(appointment.getAppointmentDate());

	    response.setAppointmentTime(appointment.getAppointmentTime());

	    response.setReason(appointment.getReason());

	    response.setSymptoms(appointment.getSymptoms());

	    response.setStatus(appointment.getStatus());

	    response.setRemarks(appointment.getRemarks());

	    response.setCreatedAt(appointment.getCreatedAt());

	    return response;
	}   	
	
}