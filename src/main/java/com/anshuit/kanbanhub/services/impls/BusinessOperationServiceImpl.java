package com.anshuit.kanbanhub.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.entities.Employee;
import com.anshuit.kanbanhub.entities.Project;
import com.anshuit.kanbanhub.entities.Task;
import com.anshuit.kanbanhub.exceptions.CustomException;
import com.anshuit.kanbanhub.repositories.EmployeeRepository;
import com.anshuit.kanbanhub.repositories.ProjectRepository;
import com.anshuit.kanbanhub.repositories.TaskRepository;

@Service
public class BusinessOperationServiceImpl {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskServiceImpl taskService;
	
	@Autowired
	private EmployeeServiceImpl employeeService;
	
	@Autowired
	private ProjectServiceImpl projectService;

	public Project addEmployeeToProject(int employeeId, int projectId) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new CustomException(GlobalConstants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId,
						HttpStatus.NOT_FOUND));

		Project project = projectRepository.findById(projectId).orElseThrow(
				() -> new CustomException(GlobalConstants.PROJECT_NOT_FOUND_WITH_ID + projectId, HttpStatus.NOT_FOUND));

		for (Employee employeeInProject : project.getEmployees()) {
			if (employeeInProject.getEmployeeId() == employeeId) {
				throw new CustomException(GlobalConstants.EMPLOYEE_ALREADY_ALLOCATED_TO_PROJECT + employee.getEmail(),
						HttpStatus.BAD_REQUEST);
			}
		}

		project.getEmployees().add(employee);
		Project updatedProject = projectRepository.save(project);
		return updatedProject;
	}

	public Project addTaskWithIdToProject(int taskId, int projectId) {
		Task task = taskRepository.findById(taskId).orElseThrow(
				() -> new CustomException(GlobalConstants.TASK_NOT_FOUND_WITH_ID + taskId, HttpStatus.NOT_FOUND));

		Project project = projectRepository.findById(projectId).orElseThrow(
				() -> new CustomException(GlobalConstants.PROJECT_NOT_FOUND_WITH_ID + projectId, HttpStatus.NOT_FOUND));
		
		project.getTasks().add(task);
		Project updatedProject = projectRepository.save(project);
		return updatedProject;
	}

	public Project addTaskToProject(Task task, int projectId) {
		Project project = projectService.getProjectById(projectId);
		Task savedTask = taskService.createTask(task);
		project.getTasks().add(savedTask);
		Project updatedProject = projectRepository.save(project);
		return updatedProject;
	}

	public Task addEmployeeToTask(int employeeId, int taskId) {
		Task task = taskService.getTaskById(taskId);
		Employee employee = employeeService.getEmployeeById(employeeId);
		task.setEmployee(employee);
		Task updatedTask = taskRepository.save(task);
		return updatedTask;
	}
}