package com.anshuit.kanbanhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anshuit.kanbanhub.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
}
