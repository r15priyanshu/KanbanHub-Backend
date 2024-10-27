package com.anshuit.kanbanhub.services.impls;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.entities.Task;
import com.anshuit.kanbanhub.enums.TaskStatusEnum;
import com.anshuit.kanbanhub.exceptions.CustomException;
import com.anshuit.kanbanhub.repositories.TaskRepository;

@Service
public class TaskServiceImpl {

	@Autowired
	private TaskRepository taskRepository;

	public Task createTask(Task task) {
		task.setTaskStatus(TaskStatusEnum.UN_ASSIGNED);
		task.setStartDate(new Date());
		return taskRepository.save(task);
	}

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	public Task getTaskById(int taskId) {
		return taskRepository.findById(taskId).orElseThrow(
				() -> new CustomException(GlobalConstants.TASK_NOT_FOUND_WITH_ID + taskId, HttpStatus.NOT_FOUND));
	}

	public Optional<Task> getTaskByIdOptional(int taskId) {
		return taskRepository.findById(taskId);
	}
}
