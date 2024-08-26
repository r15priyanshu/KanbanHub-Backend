package com.anshuit.kanbanhub.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anshuit.kanbanhub.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	Optional<Employee> findByEmail(String email);
}
