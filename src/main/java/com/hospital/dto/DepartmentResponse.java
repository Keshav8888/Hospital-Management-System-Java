package com.hospital.dto;

import com.hospital.enums.Status;

public class DepartmentResponse {

	private Long id;
	private String name;
	private String description;
	private String location;
	private Status status;
	
	public DepartmentResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepartmentResponse(Long id, String name, String description, String location, Status status) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
