package com.hospital.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.dto.ReceptionistProfileUpdateRequest;
import com.hospital.dto.ReceptionistRegisterRequest;
import com.hospital.dto.ReceptionistResponse;
import com.hospital.dto.ReceptionistUpdateRequest;
import com.hospital.entity.Receptionist;
import com.hospital.entity.User;
import com.hospital.enums.Role;
import com.hospital.enums.Status;
import com.hospital.repository.ReceptionistRepository;
import com.hospital.repository.UserRepository;

@Service
public class ReceptionistService {

	private final UserRepository userRepository;

	private final ReceptionistRepository receptionistRepository;

	private final PasswordEncoder passwordEncoder;
	
	public ReceptionistService(UserRepository userRepository, ReceptionistRepository receptionistRepository, PasswordEncoder passwordEncoder) {

		this.userRepository = userRepository;
		this.receptionistRepository = receptionistRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
    public void registerReceptionist(ReceptionistRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        if (receptionistRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone number already exists.");
        }

        User user = new User();

        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.RECEPTIONIST);

        user.setStatus(Status.ACTIVE);

        userRepository.save(user);

        Receptionist receptionist = new Receptionist();

        receptionist.setUser(user);

        receptionist.setFirstName(request.getFirstName());

        receptionist.setLastName(request.getLastName());

        receptionist.setGender(request.getGender());

        receptionist.setDateOfBirth(request.getDateOfBirth());

        receptionist.setPhone(request.getPhone());

        receptionist.setAddress(request.getAddress());

        receptionist.setStatus(Status.ACTIVE);

        receptionistRepository.save(receptionist);
    }
	
	@Transactional(readOnly = true)
	public ReceptionistResponse getMyProfile(Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElseThrow(() ->
	                    new RuntimeException("User not found."));

	    Receptionist receptionist = receptionistRepository.findByUserId(user.getId()).orElseThrow(() ->
	                    new RuntimeException("Receptionist not found."));

	    ReceptionistResponse response = new ReceptionistResponse();

	    response.setId(receptionist.getId());

	    response.setFirstName(receptionist.getFirstName());

	    response.setLastName(receptionist.getLastName());

	    response.setGender(receptionist.getGender());

	    response.setDateOfBirth(receptionist.getDateOfBirth());

	    response.setPhone(receptionist.getPhone());

	    response.setAddress(receptionist.getAddress());

	    response.setEmail(user.getEmail());

	    response.setStatus(receptionist.getStatus());

	    return response;
	}
	
	@Transactional
	public void updateMyProfile(ReceptionistProfileUpdateRequest request, Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElseThrow(() ->
	                    new RuntimeException("User not found."));

	    Receptionist receptionist = receptionistRepository.findByUserId(user.getId()).orElseThrow(() ->
	                    new RuntimeException("Receptionist not found."));

	    if (!receptionist.getPhone().equals(request.getPhone()) && receptionistRepository.existsByPhone(request.getPhone())) {

	        throw new RuntimeException("Phone number already exists.");
	    }

	    receptionist.setFirstName(request.getFirstName());

	    receptionist.setLastName(request.getLastName());

	    receptionist.setGender(request.getGender());

	    receptionist.setDateOfBirth(request.getDateOfBirth());

	    receptionist.setPhone(request.getPhone());

	    receptionist.setAddress(request.getAddress());

	    receptionistRepository.save(receptionist);
	}
	
//	public List<ReceptionistResponse> getAllReceptionists() {
//
//		List<Receptionist> receptionists = receptionistRepository.findByStatus(Status.ACTIVE);
//	    
//		return receptionists.stream().map(this::mapToResponse).collect(Collectors.toList());
//	}
	
	public Page<ReceptionistResponse> getReceptionists(String keyword, int page, int size, String sortBy, String sortDir) {

		 Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

		    Sort sort = Sort.by(direction, sortBy);
		
	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Receptionist> receptionistPage;

	    if (keyword == null || keyword.trim().isEmpty()) {

	        receptionistPage = receptionistRepository.findAll(pageable);

	    } else {

	        receptionistPage = receptionistRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrUserEmailContainingIgnoreCase(keyword, keyword, keyword, keyword, pageable);
	    }

	    return receptionistPage.map(this::mapToResponse);
	}
	
	public ReceptionistResponse getReceptionistById(Long id) {

	    Receptionist receptionist = receptionistRepository.findById(id).orElseThrow(() ->
	                    new RuntimeException("Receptionist not found."));

	    return mapToResponse(receptionist);
	}
	
	@Transactional
	public void updateReceptionist(Long id,ReceptionistUpdateRequest request) {

	    Receptionist receptionist = receptionistRepository.findById(id).orElseThrow(() ->
	                    new RuntimeException("Receptionist not found."));

	    receptionist.setFirstName(request.getFirstName());

	    receptionist.setLastName(request.getLastName());

	    receptionist.setGender(request.getGender());

	    receptionist.setDateOfBirth(request.getDateOfBirth());

	    if (!receptionist.getPhone().equals(request.getPhone()) && receptionistRepository.existsByPhone(request.getPhone())) {
	        throw new RuntimeException("Phone number already exists.");
	    }
	    
	    receptionist.setPhone(request.getPhone());

	    receptionist.setAddress(request.getAddress());

	    receptionistRepository.save(receptionist);
	}
	
	@Transactional
	public void deleteReceptionist(Long id) {

	    Receptionist receptionist = receptionistRepository.findById(id).orElseThrow(() ->
	                    new RuntimeException("Receptionist not found."));

	    receptionist.setStatus(Status.INACTIVE);

	    receptionistRepository.save(receptionist);
	}
	
	private ReceptionistResponse mapToResponse(Receptionist receptionist) {

	    ReceptionistResponse response = new ReceptionistResponse();

	    response.setId(receptionist.getId());

	    response.setFirstName(receptionist.getFirstName());

	    response.setLastName(receptionist.getLastName());

	    response.setEmail(receptionist.getUser().getEmail());

	    response.setGender(receptionist.getGender());
	    
	    response.setDateOfBirth(receptionist.getDateOfBirth());

	    response.setPhone(receptionist.getPhone());

	    response.setAddress(receptionist.getAddress());

	    response.setStatus(receptionist.getStatus());
	    
	    return response;
	}
	

}
