package io.leantech.employeemanager.services;

import java.util.List;

public interface EmployeeService {
	void createEmployee(NewEmployeeDto newEmployeeData);
	void updateEmployee(long employeeId, NewEmployeeDto newEmployee);
	void deleteEmployee(long employeeId);
	List<CompleteEmployeeDto> getEmployees(String name, String position);
	List<PositionDto> getPositions();
}
