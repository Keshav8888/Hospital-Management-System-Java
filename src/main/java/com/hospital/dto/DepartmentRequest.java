package com.hospital.dto;

public class DepartmentRequest {

	private String name;
	private String description;
	private String location;
	
	public DepartmentRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepartmentRequest(String name, String description, String location) {
		super();
		this.name = name;
		this.description = description;
		this.location = location;
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
	
	
}
