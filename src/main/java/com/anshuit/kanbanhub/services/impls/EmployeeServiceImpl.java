package com.anshuit.kanbanhub.services.impls;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.entities.Employee;
import com.anshuit.kanbanhub.entities.Role;
import com.anshuit.kanbanhub.exceptions.CustomException;
import com.anshuit.kanbanhub.repositories.EmployeeRepository;
import com.anshuit.kanbanhub.repositories.RoleRepository;
import com.anshuit.kanbanhub.utils.CustomUtil;

@Service
public class EmployeeServiceImpl {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUtil customUtil;

	public Employee createEmployee(Employee employee) {
		// First check if employee is not already registered.
		Optional<Employee> optional = employeeRepository.findByEmail(employee.getEmail());
		if (optional.isPresent()) {
			throw new CustomException(GlobalConstants.EMPLOYEE_ALREADY_EXIST_WITH_EMAIL + employee.getEmail(),
					HttpStatus.BAD_REQUEST);
		}

		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		employee.setProfilePic(GlobalConstants.DEFAULT_PROFILE_PIC_NAME);
		Role role = roleRepository.findById(1).orElse(null);
		employee.setRole(role);
		return employeeRepository.save(employee);
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();

	}

	public Employee getEmployeeById(int employeeId) {
		return employeeRepository.findById(employeeId)
				.orElseThrow(() -> new CustomException(GlobalConstants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId,
						HttpStatus.NOT_FOUND));
	}

	public Optional<Employee> getEmployeeByIdOptinal(int employeeId) {
		return employeeRepository.findById(employeeId);
	}

	public Employee getEmployeeByEmail(String email) {
		return employeeRepository.findByEmail(email).orElseThrow(
				() -> new CustomException(GlobalConstants.EMPLOYEE_NOT_FOUND_WITH_EMAIL + email, HttpStatus.NOT_FOUND));
	}

	public Optional<Employee> getEmployeeByEmailOptional(String email) {
		return employeeRepository.findByEmail(email);
	}

	public Employee updateEmployeeById(Employee employee, int employeeId) {
		Optional<Employee> optional = employeeRepository.findById(employeeId);
		if (optional.isEmpty()) {
			throw new CustomException(GlobalConstants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId, HttpStatus.NOT_FOUND);
		}

		Employee foundEmployee = optional.get();
		foundEmployee.setFirstName(employee.getFirstName());
		foundEmployee.setLastName(employee.getLastName());
		foundEmployee.getAddress().setCity(employee.getAddress().getCity());
		foundEmployee.getAddress().setState(employee.getAddress().getState());

		return employeeRepository.save(foundEmployee);
	}

	public Employee updateProfilePictureByEmployeeId(MultipartFile file, Integer employeeId) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new CustomException(GlobalConstants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId,
						HttpStatus.NOT_FOUND));

		String filename = file.getOriginalFilename();
		if (file != null && customUtil.isImageHavingValidExtension(filename)) {
			try {
				byte[] imageData = file.getBytes();
				String profilePicName = UUID.randomUUID().toString().concat(customUtil.getFileExtension(filename));
				employee.setProfilePicData(imageData);
				employee.setProfilePic(profilePicName);
			} catch (Exception e) {
				throw new CustomException(GlobalConstants.ERROR_IN_UPDATING_PROFILE_PICTURE,
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			throw new CustomException(GlobalConstants.NOT_AN_ALLOWED_IMAGE_EXTENSION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return employeeRepository.save(employee);
	}

	public byte[] getEmployeeProfilePicData(int employeeId) {
		Employee employee = this.getEmployeeById(employeeId);
		if (employee == null || employee.getProfilePicData() == null) {
			throw new CustomException(GlobalConstants.PROFILE_PICTURE_NOT_PRESENT, HttpStatus.NOT_FOUND);
		}

		return employee.getProfilePicData();
	}

	public Employee deleteEmployeeById(int employeeId) {
		Employee employee = this.getEmployeeById(employeeId);
		employee.setRole(null);
		employeeRepository.delete(employee);
		return employee;
	}

	public List<Employee> deleteAllEmployees() {
		List<Employee> allEmployees = employeeRepository.findAll();
		for (Employee employee : allEmployees) {
			employee.setRole(null);
		}
		employeeRepository.deleteAll(allEmployees);
		return allEmployees;
	}
}
