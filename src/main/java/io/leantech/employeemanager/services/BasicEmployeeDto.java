package io.leantech.employeemanager.services;

public class BasicEmployeeDto {
	private final long id;
	private final double salary;
	private final PersonDto person;
	
	public BasicEmployeeDto(long id, double salary, PersonDto person) {
		this.id = id;
		this.salary = salary;
		this.person = person;
	}

	public long getId() {
		return id;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public PersonDto getPerson() {
		return person;
	}
}
