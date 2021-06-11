package io.leantech.employeemanager.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.leantech.employeemanager.services.CompleteEmployeeDto;
import io.leantech.employeemanager.services.EmployeeService;
import io.leantech.employeemanager.services.NewEmployeeDto;
import io.leantech.employeemanager.services.PositionDto;

@Controller
@CrossOrigin
@RequestMapping("employee")
public class EmployeeController {
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public void illegalArgument() {
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public void valueNotPresent() {
	}
	
	@PostMapping("/add")
	@ResponseStatus(value = HttpStatus.OK)
	public void add(@RequestBody NewEmployeeDto newEmployee) {
		employeeService.createEmployee(newEmployee);
	}
	
	@PutMapping("/update/{employeeId}")
	@ResponseStatus(value = HttpStatus.OK)
	public void update(
			@PathVariable long employeeId,
			@RequestBody NewEmployeeDto newEmployeeDto) {
		employeeService.updateEmployee(employeeId, newEmployeeDto);
	}
	
	@DeleteMapping("/delete/{employeeId}")
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable long employeeId) {
		employeeService.deleteEmployee(employeeId);
	}
	
	@GetMapping("/filter")
	public @ResponseBody List<CompleteEmployeeDto> getEmployees(
			@RequestParam String name, @RequestParam String position) {
		return employeeService.getEmployees(name, position);
	}
	
	@GetMapping("/positions")
	public @ResponseBody List<PositionDto> getPositions() {
		return employeeService.getPositions();
	}
}
