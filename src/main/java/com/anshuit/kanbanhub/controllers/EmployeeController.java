package com.anshuit.kanbanhub.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.kanbanhub.dtos.EmployeeDto;
import com.anshuit.kanbanhub.entities.Employee;
import com.anshuit.kanbanhub.services.impls.DataTransferService;
import com.anshuit.kanbanhub.services.impls.EmployeeServiceImpl;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeServiceImpl employeeService;

	@Autowired
	private DataTransferService dataTransferService;

	@PostMapping
	public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
		Employee savedEmployee = employeeService
				.createEmployee(dataTransferService.mapEmployeeDtoToEmployee(employeeDto));
		EmployeeDto savedEmployeeDto = dataTransferService.mapEmployeeToEmployeeDto(savedEmployee);
		return new ResponseEntity<>(savedEmployeeDto, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
		List<Employee> allEmployees = employeeService.getAllEmployees();
		List<EmployeeDto> allEmployeesDto = allEmployees.stream()
				.map(employee -> dataTransferService.mapEmployeeToEmployeeDto(employee)).toList();
		return new ResponseEntity<>(allEmployeesDto, HttpStatus.OK);
	}

	@GetMapping("/{employeeId}")
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("employeeId") int employeeId) {
		Employee employee = employeeService.getEmployeeById(employeeId);
		EmployeeDto employeeDto = dataTransferService.mapEmployeeToEmployeeDto(employee);
		return new ResponseEntity<>(employeeDto, HttpStatus.OK);
	}
	
	@PutMapping("/{employeeId}")
	public ResponseEntity<EmployeeDto> updateEmployeeById(@RequestBody EmployeeDto employeeDto,@PathVariable("employeeId") int employeeId) {
		Employee updatedEmployee = employeeService
				.updateEmployeeById(dataTransferService.mapEmployeeDtoToEmployee(employeeDto),employeeId);
		EmployeeDto updatedEmployeeDto = dataTransferService.mapEmployeeToEmployeeDto(updatedEmployee);
		return new ResponseEntity<>(updatedEmployeeDto, HttpStatus.OK);
	}

	@DeleteMapping("/{employeeId}")
	public ResponseEntity<EmployeeDto> deleteEmployeeById(@PathVariable("employeeId") int employeeId) {
		Employee employee = employeeService.deleteEmployeeById(employeeId);
		EmployeeDto employeeDto = dataTransferService.mapEmployeeToEmployeeDto(employee);
		return new ResponseEntity<>(employeeDto, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<List<EmployeeDto>> deleteAllEmployees() {
		List<Employee> allEmployees = employeeService.deleteAllEmployees();
		List<EmployeeDto> allEmployeesDto = allEmployees.stream()
				.map(employee -> dataTransferService.mapEmployeeToEmployeeDto(employee)).toList();
		return new ResponseEntity<>(allEmployeesDto, HttpStatus.OK);
	}
}
