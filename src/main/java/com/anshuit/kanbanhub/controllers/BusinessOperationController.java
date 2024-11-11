package com.anshuit.kanbanhub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.kanbanhub.dtos.ProjectDtoFull;
import com.anshuit.kanbanhub.dtos.TaskDto;
import com.anshuit.kanbanhub.entities.Project;
import com.anshuit.kanbanhub.entities.Task;
import com.anshuit.kanbanhub.services.impls.BusinessOperationServiceImpl;
import com.anshuit.kanbanhub.services.impls.DataTransferService;

@RestController
@RequestMapping("/businessoperation")
public class BusinessOperationController {

	@Autowired
	private BusinessOperationServiceImpl businessOperationService;

	@Autowired
	private DataTransferService dataTransferService;

	@GetMapping("/addemployee/{employeeId}/project/{projectId}")
	public ResponseEntity<ProjectDtoFull> addEmployeeToProject(@PathVariable("employeeId") int employeeId,
			@PathVariable("projectId") int projectId) {
		Project project = businessOperationService.addEmployeeToProject(employeeId, projectId);
		ProjectDtoFull projectDto = dataTransferService.mapProjectToProjectDtoFull(project);
		return new ResponseEntity<>(projectDto, HttpStatus.OK);
	}

	@GetMapping("/addtask/{taskId}/project/{projectId}")
	public ResponseEntity<ProjectDtoFull> addTaskWithIdToProject(@PathVariable("taskId") int taskId,
			@PathVariable("projectId") int projectId) {
		Project project = businessOperationService.addTaskWithIdToProject(taskId, projectId);
		ProjectDtoFull projectDto = dataTransferService.mapProjectToProjectDtoFull(project);
		return new ResponseEntity<>(projectDto, HttpStatus.OK);
	}

	@PostMapping("/addtask/project/{projectId}")
	public ResponseEntity<ProjectDtoFull> addTaskToProject(@RequestBody TaskDto taskDto,
			@PathVariable("projectId") int projectId) {
		Task task = dataTransferService.mapTaskDtoToTask(taskDto);
		Project project = businessOperationService.addTaskToProject(task, projectId);
		ProjectDtoFull projectDto = dataTransferService.mapProjectToProjectDtoFull(project);
		return new ResponseEntity<>(projectDto, HttpStatus.OK);
	}

	@GetMapping("/addemployee/{employeeId}/task/{taskId}")
	public ResponseEntity<TaskDto> addEmployeeToTask(@PathVariable("employeeId") int employeeId,
			@PathVariable("taskId") int taskId) {
		Task task = businessOperationService.addEmployeeToTask(employeeId, taskId);
		TaskDto taskDto = dataTransferService.mapTaskToTaskDto(task);
		return new ResponseEntity<>(taskDto, HttpStatus.OK);
	}
}
