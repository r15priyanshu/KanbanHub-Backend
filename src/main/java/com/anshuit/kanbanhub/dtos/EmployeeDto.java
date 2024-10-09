package com.anshuit.kanbanhub.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
	private int employeeId;
	private String employeeDisplayId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String profilePic;
	private AddressDto address;
	private RoleDto role;
}
