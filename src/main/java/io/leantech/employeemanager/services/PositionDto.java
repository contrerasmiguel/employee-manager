package io.leantech.employeemanager.services;

import java.util.List;

public class PositionDto {
	private final long id;
	private final String name;
	private final List<BasicEmployeeDto> employees;
	
	public PositionDto(long id, String name, List<BasicEmployeeDto> employees) {
		this.id = id;
		this.name = name;
		this.employees = employees;
	}

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<BasicEmployeeDto> getEmployees() {
		return employees;
	}
}
