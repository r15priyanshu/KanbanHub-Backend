package com.anshuit.kanbanhub.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anshuit.kanbanhub.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	List<Project> findAll(Sort sort);

	@Query("SELECT p FROM Project p")
	List<Project> findAllPartial();

	@Query("SELECT p FROM Project p")
	List<Project> findAllPartial(Sort sort);
}
