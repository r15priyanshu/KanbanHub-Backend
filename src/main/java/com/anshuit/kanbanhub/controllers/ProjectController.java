package com.anshuit.kanbanhub.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.kanbanhub.dtos.ProjectDto;
import com.anshuit.kanbanhub.entities.Project;
import com.anshuit.kanbanhub.services.impls.DataTransferService;
import com.anshuit.kanbanhub.services.impls.ProjectServiceImpl;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private ProjectServiceImpl projectService;

	@Autowired
	private DataTransferService dataTransferService;

	@PostMapping
	public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
		Project createdProject = projectService.createProject(dataTransferService.mapProjectDtoToProject(projectDto));
		ProjectDto createdProjectDto = dataTransferService.mapProjectToProjectDto(createdProject);
		return new ResponseEntity<>(createdProjectDto, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ProjectDto>> getAllProjects() {
		List<Project> allProjects = projectService.getAllProjects();
		List<ProjectDto> allProjectsDto = allProjects.stream()
				.map(project -> dataTransferService.mapProjectToProjectDto(project)).toList();
		return new ResponseEntity<>(allProjectsDto, HttpStatus.OK);
	}

	@GetMapping("/{projectId}")
	@Transactional
	public ResponseEntity<ProjectDto> getProjectById(@PathVariable("projectId") int projectId) throws InterruptedException {
		Project project = projectService.getProjectById(projectId);
		ProjectDto projectDto = dataTransferService.mapProjectToProjectDto(project);
		return new ResponseEntity<>(projectDto, HttpStatus.OK);
	}
}
