package com.anshuit.kanbanhub.entities;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role implements GrantedAuthority {
	private static final long serialVersionUID = -7948623012668520149L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;
	private String roleName;

	public Role(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String getAuthority() {
		return this.roleName;
	}
}
