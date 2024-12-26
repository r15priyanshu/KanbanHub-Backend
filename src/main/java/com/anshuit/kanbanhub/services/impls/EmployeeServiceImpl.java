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
import com.anshuit.kanbanhub.enums.ExceptionDetailsEnum;
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

	public Employee saveOrUpdateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public Employee createEmployee(Employee employee) {
		// First check if employee is not already registered.
		Optional<Employee> optional = this.getEmployeeByEmailOptional(employee.getEmail());
		if (optional.isPresent()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, ExceptionDetailsEnum.EMPLOYEE_ALREADY_EXIST_WITH_EMAIL,
					employee.getEmail());
		}

		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		employee.setProfilePic(GlobalConstants.DEFAULT_PROFILE_PIC_NAME);
		Role role = roleRepository.findById(1).orElse(null);
		employee.setRole(role);
		return this.saveOrUpdateEmployee(employee);
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();

	}

	public Employee getEmployeeByEmployeeDisplayId(String employeeDisplayId) {
		int employeeId = this.extractEmployeeIdFromEmployeeDisplayId(employeeDisplayId);
		return employeeRepository.findById(employeeId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
				ExceptionDetailsEnum.EMPLOYEE_NOT_FOUND_WITH_DISPLAY_ID, employeeDisplayId));
	}

	public Optional<Employee> getEmployeeByIdOptional(int employeeId) {
		return employeeRepository.findById(employeeId);
	}

	public Employee getEmployeeByEmail(String email) {
		return employeeRepository.findByEmail(email).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
				ExceptionDetailsEnum.EMPLOYEE_NOT_FOUND_WITH_EMAIL, email));
	}

	public Optional<Employee> getEmployeeByEmailOptional(String email) {
		return employeeRepository.findByEmail(email);
	}

	public Employee updateEmployeeByEmployeeDisplayId(Employee employee, String employeeDisplayId) {
		Employee foundEmployee = this.getEmployeeByEmployeeDisplayId(employeeDisplayId);
		foundEmployee.setFirstName(employee.getFirstName());
		foundEmployee.setLastName(employee.getLastName());
		foundEmployee.getAddress().setCity(employee.getAddress().getCity());
		foundEmployee.getAddress().setState(employee.getAddress().getState());
		return employeeRepository.save(foundEmployee);
	}

	public Employee updateProfilePictureByEmployeeDisplayId(MultipartFile file, String employeeDisplayId) {
		Employee employee = this.getEmployeeByEmployeeDisplayId(employeeDisplayId);
		String filename = file.getOriginalFilename();
		if (file != null && customUtil.isImageHavingValidExtensionForProfilePicture(filename)) {
			try {
				byte[] imageData = file.getBytes();
				String profilePicName = UUID.randomUUID().toString().concat(customUtil.getFileExtension(filename));
				employee.setProfilePicData(imageData);
				employee.setProfilePic(profilePicName);
			} catch (Exception e) {
				throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
						ExceptionDetailsEnum.ERROR_IN_UPDATING_PROFILE_PICTURE);
			}
		} else {
			throw new CustomException(HttpStatus.BAD_REQUEST, ExceptionDetailsEnum.NOT_AN_ALLOWED_IMAGE_EXTENSION);
		}
		return employeeRepository.save(employee);
	}

	public Employee removeProfilePictureByEmployeeDisplayId(String employeeDisplayId) {
		Employee employee = this.getEmployeeByEmployeeDisplayId(employeeDisplayId);
		employee.setProfilePic(GlobalConstants.DEFAULT_PROFILE_PIC_NAME);
		employee.setProfilePicData(null);
		return employeeRepository.save(employee);
	}

	public byte[] getEmployeeProfilePicDataByEmployeeDisplayId(String employeeDisplayId) {
		Employee employee = this.getEmployeeByEmployeeDisplayId(employeeDisplayId);
		if (employee == null || employee.getProfilePicData() == null) {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.PROFILE_PICTURE_NOT_PRESENT);
		}
		return employee.getProfilePicData();
	}

	public Employee deleteEmployeeByEmployeeDisplayId(String employeeDisplayId) {
		Employee employee = this.getEmployeeByEmployeeDisplayId(employeeDisplayId);
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

	public int extractEmployeeIdFromEmployeeDisplayId(String employeeDisplayId) {
		if (!employeeDisplayId.startsWith(GlobalConstants.DEFAULT_EMPLOYEE_DISPLAY_ID_PREFIX)) {
			throw new CustomException(HttpStatus.BAD_REQUEST,
					ExceptionDetailsEnum.EMPLOYEE_DISPLAY_ID_NOT_STARTING_WITH_PREFIX);
		}

		try {
			return Integer
					.parseInt(employeeDisplayId.substring(GlobalConstants.DEFAULT_EMPLOYEE_DISPLAY_ID_PREFIX.length()));
		} catch (NumberFormatException e) {
			throw new CustomException(HttpStatus.BAD_REQUEST,
					ExceptionDetailsEnum.EMPLOYEE_ID_FROM_EMPLOYEE_DISPLAY_ID_PARSING_ERROR);
		}
	}
}
