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

	@GetMapping("/addemployee/{employeeDisplayId}/project/{projectDisplayId}")
	public ResponseEntity<ProjectDtoFull> addEmployeeToProject(
			@PathVariable("employeeDisplayId") String employeeDisplayId,
			@PathVariable("projectDisplayId") String projectDisplayId) {
		Project project = businessOperationService.addEmployeeToProjectByDisplayIds(employeeDisplayId,
				projectDisplayId);
		ProjectDtoFull projectDto = dataTransferService.mapProjectToProjectDtoFull(project);
		return new ResponseEntity<>(projectDto, HttpStatus.OK);
	}

	@GetMapping("/addtask/{taskDisplayId}/project/{projectDisplayId}")
	public ResponseEntity<ProjectDtoFull> addTaskWithIdToProject(@PathVariable("taskDisplayId") String taskDisplayId,
			@PathVariable("projectDisplayId") String projectDisplayId) {
		Project project = businessOperationService.addTaskWithIdToProjectWithIdByDisplayId(taskDisplayId,
				projectDisplayId);
		ProjectDtoFull projectDto = dataTransferService.mapProjectToProjectDtoFull(project);
		return new ResponseEntity<>(projectDto, HttpStatus.OK);
	}

	@PostMapping("/addtask/project/{projectDisplayId}")
	public ResponseEntity<ProjectDtoFull> addTaskToProject(@RequestBody TaskDto taskDto,
			@PathVariable("projectDisplayId") String projectDisplayId) {
		Task task = dataTransferService.mapTaskDtoToTask(taskDto);
		Project project = businessOperationService.addTaskToProjectByDisplayId(task, projectDisplayId);
		ProjectDtoFull projectDto = dataTransferService.mapProjectToProjectDtoFull(project);
		return new ResponseEntity<>(projectDto, HttpStatus.OK);
	}

	@GetMapping("/addemployee/{employeeDisplayId}/task/{taskDisplayId}")
	public ResponseEntity<TaskDto> addEmployeeToTask(@PathVariable("employeeDisplayId") String employeeDisplayId,
			@PathVariable("taskDisplayId") String taskDisplayId) {
		Task task = businessOperationService.addEmployeeToTaskByDisplayIds(employeeDisplayId, taskDisplayId);
		TaskDto taskDto = dataTransferService.mapTaskToTaskDto(task);
		return new ResponseEntity<>(taskDto, HttpStatus.OK);
	}
}
