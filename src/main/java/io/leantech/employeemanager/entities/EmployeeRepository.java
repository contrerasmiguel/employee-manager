package io.leantech.employeemanager.entities;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
	extends JpaRepository<Employee, Long> {
	
}
