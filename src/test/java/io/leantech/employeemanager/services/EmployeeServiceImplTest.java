package io.leantech.employeemanager.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.leantech.employeemanager.entities.EmployeeRepository;
import io.leantech.employeemanager.entities.PersonRepository;
import io.leantech.employeemanager.entities.Position;
import io.leantech.employeemanager.entities.PositionRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
	@Mock
	private PersonRepository people;
	
	@Mock
	private PositionRepository positions;
	
	@Mock
	private EmployeeRepository employees;
	
	private EmployeeService employeeService;
	
	private NewEmployeeDto employeeDummy() {
		var newEmployeeData = new NewEmployeeDto();
		
		newEmployeeData.setName("John");
		newEmployeeData.setLastName("Doe");
		newEmployeeData.setAddress("Av 1");
		newEmployeeData.setCityName("Madrid");
		newEmployeeData.setCellphone("5556661111");
		newEmployeeData.setSalary(1500.99);
		newEmployeeData.setPositionName("Front-end developer");
		
		return newEmployeeData;
	}
	
	@BeforeEach
	public void createEmployeeService() {
		employeeService = new EmployeeServiceImpl(people, positions, employees);
	}
	
	@Test
	public void shouldThrowExceptionOnNullName() {
		var employee = employeeDummy();
		
		employee.setName(null);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			employeeService.createEmployee(employee);
		});
	}
	
	@Test
	public void shouldCreatePosition() {
		var employee = employeeDummy();
		var positionName = "Back-end developer";
		
		employee.setPositionName(positionName);
		
		var newPosition = new Position();
		newPosition.setName(positionName);
		
		employeeService.createEmployee(employee);
		
		verify(positions, times(1)).save(any());
	}
}
