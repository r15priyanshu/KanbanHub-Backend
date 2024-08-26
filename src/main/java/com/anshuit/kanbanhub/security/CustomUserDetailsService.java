package com.anshuit.kanbanhub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.entities.Employee;
import com.anshuit.kanbanhub.repositories.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByEmail(username).orElseThrow(() -> {
			log.info("Inside loadUserByUsername !! %s"
					.formatted(GlobalConstants.EMPLOYEE_NOT_FOUND_WITH_EMAIL + username));
			throw new UsernameNotFoundException(GlobalConstants.EMPLOYEE_NOT_FOUND_WITH_EMAIL + username);
		});
		
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(employee.getRole().getRoleName());
		UserDetails userDetails = User.builder().username(username).password(employee.getPassword())
				.authorities(authority).build();
		return userDetails;
	}
}
