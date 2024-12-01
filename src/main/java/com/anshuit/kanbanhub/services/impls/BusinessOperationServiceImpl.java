package com.anshuit.kanbanhub.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.entities.Employee;
import com.anshuit.kanbanhub.entities.Project;
import com.anshuit.kanbanhub.entities.Task;
import com.anshuit.kanbanhub.exceptions.CustomException;

@Service
public class BusinessOperationServiceImpl {

	@Autowired
	private TaskServiceImpl taskService;

	@Autowired
	private EmployeeServiceImpl employeeService;

	@Autowired
	private ProjectServiceImpl projectService;

	public Project addEmployeeToProjectByDisplayIds(String employeeDisplayId, String projectDisplayId) {
		Employee employee = employeeService.getEmployeeByEmployeeDisplayId(employeeDisplayId);

		Project project = projectService.getProjectByProjectDisplayId(projectDisplayId);

		for (Employee employeeInProject : project.getEmployees()) {
			if (employeeInProject.getEmployeeDisplayId().equals(employeeDisplayId)) {
				throw new CustomException(GlobalConstants.EMPLOYEE_ALREADY_ALLOCATED_TO_PROJECT + employee.getEmail(),
						HttpStatus.BAD_REQUEST);
			}
		}

		project.getEmployees().add(employee);
		Project updatedProject = projectService.save(project);
		return updatedProject;
	}

	public Project addTaskWithIdToProjectWithIdByDisplayId(String taskDisplayId, String projectDisplayId) {
		Task task = taskService.getTaskByTaskDisplayId(taskDisplayId);
		Project project = projectService.getProjectByProjectDisplayId(projectDisplayId);
		project.getTasks().add(task);
		Project updatedProject = projectService.save(project);
		return updatedProject;
	}

	public Project addTaskToProjectByDisplayId(Task task, String projectDisplayId) {
		Project project = projectService.getProjectByProjectDisplayId(projectDisplayId);
		Task savedTask = taskService.createTask(task);
		project.getTasks().add(savedTask);
		Project updatedProject = projectService.save(project);
		return updatedProject;
	}

	public Task addEmployeeToTaskByDisplayIds(String employeeDisplayId, String taskDisplayId) {
		Task task = taskService.getTaskByTaskDisplayId(taskDisplayId);
		Employee employee = employeeService.getEmployeeByEmployeeDisplayId(employeeDisplayId);
		task.setEmployee(employee);
		Task updatedTask = taskService.save(task);
		return updatedTask;
	}
}