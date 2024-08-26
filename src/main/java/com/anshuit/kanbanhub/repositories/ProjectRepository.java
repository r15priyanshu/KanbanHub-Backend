package com.anshuit.kanbanhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anshuit.kanbanhub.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
