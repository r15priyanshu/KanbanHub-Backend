package com.anshuit.kanbanhub.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.kanbanhub.dtos.TaskDto;
import com.anshuit.kanbanhub.entities.Task;
import com.anshuit.kanbanhub.services.impls.DataTransferService;
import com.anshuit.kanbanhub.services.impls.TaskServiceImpl;

@RestController
public class TaskController {

	@Autowired
	private TaskServiceImpl taskService;

	@Autowired
	private DataTransferService dataTransferService;

	@PostMapping("/task")
	public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
		Task createdTask = taskService.createTask(dataTransferService.mapTaskDtoToTask(taskDto));
		TaskDto createdTaskDto = dataTransferService.mapTaskToTaskDto(createdTask);
		return new ResponseEntity<TaskDto>(createdTaskDto, HttpStatus.CREATED);
	}

	@GetMapping("/task")
	public ResponseEntity<List<TaskDto>> getAllTasks() {
		List<Task> allTasks = taskService.getAllTasks();
		List<TaskDto> allTasksDto = allTasks.stream().map(task -> dataTransferService.mapTaskToTaskDto(task)).toList();
		return new ResponseEntity<>(allTasksDto, HttpStatus.OK);
	}

	@GetMapping("/task/{taskDisplayId}")
	public ResponseEntity<TaskDto> getTaskById(@PathVariable("taskDisplayId") String taskDisplayId) {
		Task task = taskService.getTaskByTaskDisplayId(taskDisplayId);
		TaskDto taskDto = dataTransferService.mapTaskToTaskDto(task);
		return new ResponseEntity<>(taskDto, HttpStatus.OK);
	}
}
