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

	public Task save(Task task) {
		return taskRepository.save(task);
	}

	public Task createTask(Task task) {
		task.setTaskStatus(TaskStatusEnum.UN_ASSIGNED);
		task.setStartDate(new Date());
		return taskRepository.save(task);
	}

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	public Task getTaskByTaskDisplayId(String taskDisplayId) {
		int taskId = this.extractTaskIdFromTaskDisplayId(taskDisplayId);
		return this.getTaskByIdOptional(taskId).orElseThrow(() -> new CustomException(
				GlobalConstants.TASK_NOT_FOUND_WITH_TASK_DISPLAY_ID + taskDisplayId, HttpStatus.NOT_FOUND));
	}

	public Optional<Task> getTaskByIdOptional(int taskId) {
		return taskRepository.findById(taskId);
	}

	public int extractTaskIdFromTaskDisplayId(String taskDisplayId) {
		if (!taskDisplayId.startsWith(GlobalConstants.DEFAULT_TASK_DISPLAY_ID_PREFIX)) {
			throw new CustomException(GlobalConstants.TASK_DISPLAY_ID_NOT_STARTING_WITH_PREFIX, HttpStatus.BAD_REQUEST);
		}

		try {
			return Integer.parseInt(taskDisplayId.substring(GlobalConstants.DEFAULT_TASK_DISPLAY_ID_PREFIX.length()));
		} catch (NumberFormatException e) {
			throw new CustomException(GlobalConstants.TASK_ID_FROM_TASK_DISPLAY_ID_PARSING_ERROR,
					HttpStatus.BAD_REQUEST);
		}
	}
}
