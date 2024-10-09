package com.anshuit.kanbanhub.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anshuit.kanbanhub.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	Optional<Project> findProjectByProjectDisplayId(String projectDisplayId);
}
