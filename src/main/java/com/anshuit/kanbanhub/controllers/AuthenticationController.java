package com.anshuit.kanbanhub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.dtos.EmployeeDto;
import com.anshuit.kanbanhub.dtos.LoginRequestDto;
import com.anshuit.kanbanhub.entities.Employee;
import com.anshuit.kanbanhub.exceptions.CustomException;
import com.anshuit.kanbanhub.security.MyJwtUtil;
import com.anshuit.kanbanhub.services.impls.DataTransferService;
import com.anshuit.kanbanhub.services.impls.EmployeeServiceImpl;
import com.anshuit.kanbanhub.services.impls.RefreshTokenServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

	@Autowired
	private MyJwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmployeeServiceImpl employeeService;

	@Autowired
	private RefreshTokenServiceImpl refreshTokenService;

	@Autowired
	private DataTransferService dataTransferService;

	@PostMapping(GlobalConstants.LOGIN_URL)
	private ResponseEntity<EmployeeDto> performLogin(@RequestBody LoginRequestDto loginRequestDto,
			HttpServletResponse response) {
		// Create Unauthenticated Token First
		UsernamePasswordAuthenticationToken unauthenticatedToken = UsernamePasswordAuthenticationToken
				.unauthenticated(loginRequestDto.getEmail(), loginRequestDto.getPassword());
		String token = null;
		Employee employee = null;
		try {
			Authentication authenticate = authenticationManager.authenticate(unauthenticatedToken);
			// GENERATE THE TOKEN NOW
			employee = employeeService.getEmployeeByEmail(authenticate.getName());
			UserDetails userDetails = User.builder().username(employee.getEmail()).password(employee.getPassword())
					.authorities(employee.getRole()).build();
			token = jwtUtil.generateToken(userDetails);
			SecurityContextHolder.getContext().setAuthentication(authenticate);
		} catch (Exception e) {
			if (e instanceof UsernameNotFoundException) {
				throw new CustomException(e.getMessage(), HttpStatus.NOT_FOUND);
			} else if (e instanceof BadCredentialsException) {
				throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
			} else {
				e.printStackTrace();
				throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		// Setting The Token In Response Header
		response.setHeader(GlobalConstants.JWT_TOKEN_RESPONSE_HEADER_KEY, token);
		response.setHeader(GlobalConstants.JWT_REFRESH_TOKEN_RESPONSE_HEADER_KEY,
				refreshTokenService.getRefreshToken(employee).getRefreshToken());
		EmployeeDto employeeDto = dataTransferService.mapEmployeeToEmployeeDto(employee);
		return new ResponseEntity<>(employeeDto, HttpStatus.OK);
	}

	@PostMapping(GlobalConstants.REGISTER_URL)
	public ResponseEntity<EmployeeDto> registerEmployee(@RequestBody EmployeeDto employeeDto) {
		Employee savedEmployee = employeeService
				.createEmployee(dataTransferService.mapEmployeeDtoToEmployee(employeeDto));
		EmployeeDto savedEmployeeDto = dataTransferService.mapEmployeeToEmployeeDto(savedEmployee);
		return new ResponseEntity<>(savedEmployeeDto, HttpStatus.CREATED);
	}
}
