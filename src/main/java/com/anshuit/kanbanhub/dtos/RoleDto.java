package com.anshuit.kanbanhub.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
	private int roleId;
	private String roleName;

	@JsonIgnore
	public String getAuthority() {
		return this.roleName;
	}
}
