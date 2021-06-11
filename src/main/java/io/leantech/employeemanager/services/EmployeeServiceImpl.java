package io.leantech.employeemanager.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.leantech.employeemanager.entities.Employee;
import io.leantech.employeemanager.entities.EmployeeRepository;
import io.leantech.employeemanager.entities.Person;
import io.leantech.employeemanager.entities.PersonRepository;
import io.leantech.employeemanager.entities.Position;
import io.leantech.employeemanager.entities.PositionRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private final PersonRepository people;
	private final PositionRepository positions;
	private final EmployeeRepository employees;
	
	public EmployeeServiceImpl(
			PersonRepository people,
			PositionRepository positions,
			EmployeeRepository employees
	) {
		this.people = people;
		this.positions = positions;
		this.employees = employees;
	}
		
	private Person createPerson(
			String name,
			String lastName,
			String address,
			String cityName,
			String cellphone) {
		var person = new Person();
		
		person.setName(name);
		person.setLastName(lastName);
		person.setAddress(address);
		person.setCityName(cityName);
		person.setCellphone(cellphone);
		
		people.save(person);
		
		return person;
	}
	
	private Position createPositionIfNotExists(String positionName) {
		return positions.findByName(positionName).stream()
				.findFirst()
				.orElseGet(() -> {
					var newPosition = new Position();
					newPosition.setName(positionName);
					positions.save(newPosition);
					return newPosition;
				});
	}
	
	private boolean compareAttr(String attr, String filter) {
		return filter == null || filter.isEmpty() || filter.equals(attr);
	}
	
	private PersonDto personDtoFromPerson(Person person) {
		return new PersonDto(
				person.getName(),
				person.getLastName(),
				person.getAddress(),
				person.getCityName(),
				person.getCellphone());
	}

	@Override
	public void createEmployee(NewEmployeeDto newEmployeeData) {
		if (newEmployeeData == null
				|| newEmployeeData.getName() == null
				|| newEmployeeData.getLastName() == null
				|| newEmployeeData.getPositionName() == null
				|| newEmployeeData.getSalary() == null)
			throw new IllegalArgumentException();
		
		var person = createPerson(
				newEmployeeData.getName(),
				newEmployeeData.getLastName(),
				newEmployeeData.getAddress(),
				newEmployeeData.getCityName(),
				newEmployeeData.getCellphone());
		
		var position = createPositionIfNotExists(newEmployeeData.getPositionName());
		
		var newEmployee = new Employee();
		
		newEmployee.setPerson(person);
		newEmployee.setPosition(position);
		newEmployee.setSalary(newEmployeeData.getSalary());
		
		employees.save(newEmployee);
	}

	@Override
	public void updateEmployee(long employeeId, NewEmployeeDto newEmployeeData) {
		if (newEmployeeData == null
				|| newEmployeeData.getName() == null
				|| newEmployeeData.getLastName() == null
				|| newEmployeeData.getPositionName() == null
				|| newEmployeeData.getSalary() == null)
			throw new IllegalArgumentException();
		
		var employee = employees.findById(employeeId).orElseThrow();
		
		var person = employee.getPerson();
		
		person.setName(newEmployeeData.getName());
		person.setLastName(newEmployeeData.getLastName());
		person.setAddress(newEmployeeData.getAddress());
		person.setCityName(newEmployeeData.getCityName());
		person.setCellphone(newEmployeeData.getCellphone());
		
		people.save(person);
		
		var position = createPositionIfNotExists(newEmployeeData.getPositionName());
		
		employee.setPosition(position);
		employee.setSalary(newEmployeeData.getSalary());
		
		employees.save(employee);
	}

	@Override
	public void deleteEmployee(long employeeId) {
		var employee = employees.findById(employeeId).orElseThrow();
		employees.delete(employee);
		people.delete(employee.getPerson());
	}

	@Override
	public List<CompleteEmployeeDto> getEmployees(String name, String position) {
		return employees.findAll().stream()
			.filter(employee ->
				compareAttr(employee.getPerson().getName(), name)
				&& compareAttr(employee.getPosition().getName(), position)
			).map(employee -> {
				var employeeDto = new CompleteEmployeeDto();
				
				employeeDto.setId(employee.getId());
				employeeDto.setSalary(employee.getSalary());
				employeeDto.setPosition(employee.getPosition().getName());
				employeeDto.setName(employee.getPerson().getName());
				employeeDto.setLastName(employee.getPerson().getLastName());
				employeeDto.setAddress(employee.getPerson().getAddress());
				employeeDto.setCityName(employee.getPerson().getCityName());
				employeeDto.setCellphone(employee.getPerson().getCellphone());
				
				return employeeDto;				
			}).collect(Collectors.toList());
	}

	@Override
	public List<PositionDto> getPositions() {
		return positions.findAll().stream()
			.map(position -> {
				var employeesByPosition = employees.findAll().stream()
						.filter(employee -> employee.getPosition().getId() == position.getId())
						.collect(Collectors.toList());
				
				var sortedEmployees = employeesByPosition.stream()
						.map(employee -> new BasicEmployeeDto(
								employee.getId(),
								employee.getSalary(),
								personDtoFromPerson(employee.getPerson())))
						.sorted(Comparator.comparing(BasicEmployeeDto::getSalary).reversed())
						.collect(Collectors.toList());
				
				if (sortedEmployees.isEmpty())
					return Optional.<PositionDto>empty();
				else
					return Optional.<PositionDto>of(new PositionDto(
							position.getId(),
							position.getName(),
							sortedEmployees));
			})
			.flatMap(Optional<PositionDto>::stream)
			.collect(Collectors.toList());
	}
}
