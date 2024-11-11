package com.anshuit.kanbanhub.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.kanbanhub.dtos.ProjectDtoBase;
import com.anshuit.kanbanhub.dtos.ProjectDtoFull;
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
	public ResponseEntity<ProjectDtoFull> createProject(@RequestBody ProjectDtoFull projectDto) {
		Project createdProject = projectService.createProject(dataTransferService.mapProjectDtoToProject(projectDto));
		ProjectDtoFull createdProjectDto = dataTransferService.mapProjectToProjectDtoFull(createdProject);
		return new ResponseEntity<>(createdProjectDto, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<? extends ProjectDtoBase>> getAllProjects(
			@RequestParam("fetchPartial") boolean fetchPartial) {
		List<Project> allProjects = null;
		List<ProjectDtoBase> allProjectsDtoFinalResult = new ArrayList<>();
		if (fetchPartial) {
			allProjects = projectService.getAllProjectsPartial();
			allProjectsDtoFinalResult.addAll(allProjects.stream()
					.map(project -> dataTransferService.mapProjectToProjectDtoBase(project)).toList());
		} else {
			allProjects = projectService.getAllProjects();
			allProjectsDtoFinalResult.addAll(allProjects.stream()
					.map(project -> dataTransferService.mapProjectToProjectDtoFull(project)).toList());
		}
		return new ResponseEntity<>(allProjectsDtoFinalResult, HttpStatus.OK);
	}

	@GetMapping("/{projectDisplayId}")
	@Transactional
	public ResponseEntity<ProjectDtoFull> getProjectByProjectDisplayId(
			@PathVariable("projectDisplayId") String projectDisplayId) throws InterruptedException {
		Project project = projectService.getProjectByProjectDisplayId(projectDisplayId);
		ProjectDtoFull projectDto = dataTransferService.mapProjectToProjectDtoFull(project);
		return new ResponseEntity<>(projectDto, HttpStatus.OK);
	}
}
