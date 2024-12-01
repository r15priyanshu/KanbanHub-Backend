package com.anshuit.kanbanhub.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.dtos.ApiResponseDto;
import com.anshuit.kanbanhub.dtos.EmployeeDto;
import com.anshuit.kanbanhub.entities.Employee;
import com.anshuit.kanbanhub.services.impls.DataTransferService;
import com.anshuit.kanbanhub.services.impls.EmployeeServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

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

	@GetMapping("/{employeeDisplayId}")
	public ResponseEntity<EmployeeDto> getEmployeeByEmployeeDisplayId(@PathVariable("employeeDisplayId") String employeeDisplayId) {
		Employee employee = employeeService.getEmployeeByEmployeeDisplayId(employeeDisplayId);
		EmployeeDto employeeDto = dataTransferService.mapEmployeeToEmployeeDto(employee);
		return new ResponseEntity<>(employeeDto, HttpStatus.OK);
	}

	@PutMapping("/{employeeDisplayId}")
	public ResponseEntity<EmployeeDto> updateEmployeeByEmployeeDisplayId(@RequestBody EmployeeDto employeeDto,
			@PathVariable("employeeDisplayId") String employeeDisplayId) {
		Employee updatedEmployee = employeeService.updateEmployeeByEmployeeDisplayId(
				dataTransferService.mapEmployeeDtoToEmployee(employeeDto), employeeDisplayId);
		EmployeeDto updatedEmployeeDto = dataTransferService.mapEmployeeToEmployeeDto(updatedEmployee);
		return new ResponseEntity<>(updatedEmployeeDto, HttpStatus.OK);
	}

	@PostMapping("/updateProfilePicture/{employeeDisplayId}")
	public ResponseEntity<ApiResponseDto> updateProfilePictureByEmployeeDisplayId(@RequestParam("image") MultipartFile image,
			@PathVariable("employeeDisplayId") String employeeDisplayId, HttpServletRequest request) {

		Employee employee = employeeService.updateProfilePictureByEmployeeDisplayId(image, employeeDisplayId);
		EmployeeDto employeeDto = dataTransferService.mapEmployeeToEmployeeDto(employee);
		ApiResponseDto apiResponseDto = ApiResponseDto.builder()
				.message(GlobalConstants.PROFILE_PICTURE_SUCCESSFULLY_UPDATED).timestamp(LocalDateTime.now())
				.status(HttpStatus.CREATED).statusCode(HttpStatus.CREATED.value()).path(request.getRequestURI())
				.data(Map.of(GlobalConstants.KEY_EMPLOYEE_LCASE, employeeDto)).build();
		return new ResponseEntity<ApiResponseDto>(apiResponseDto, HttpStatus.CREATED);
	}

	@PutMapping("/removeProfilePicture/{employeeDisplayId}")
	public ResponseEntity<ApiResponseDto> removeProfilePictureByEmployeeDisplayId(
			@PathVariable("employeeDisplayId") String employeeDisplayId, HttpServletRequest request) {
		Employee employee = employeeService.removeProfilePictureByEmployeeDisplayId(employeeDisplayId);
		EmployeeDto employeeDto = dataTransferService.mapEmployeeToEmployeeDto(employee);
		ApiResponseDto apiResponseDto = ApiResponseDto.builder()
				.message(GlobalConstants.PROFILE_PICTURE_SUCCESSFULLY_REMOVED).timestamp(LocalDateTime.now())
				.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).path(request.getRequestURI())
				.data(Map.of(GlobalConstants.KEY_EMPLOYEE_LCASE, employeeDto)).build();
		return new ResponseEntity<ApiResponseDto>(apiResponseDto, HttpStatus.OK);
	}

	@DeleteMapping("/{employeeDisplayId}")
	public ResponseEntity<EmployeeDto> deleteEmployeeByEmployeeDisplayId(@PathVariable("employeeDisplayId") String employeeDisplayId) {
		Employee employee = employeeService.deleteEmployeeByEmployeeDisplayId(employeeDisplayId);
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
