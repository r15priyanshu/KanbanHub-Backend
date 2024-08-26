package com.anshuit.kanbanhub.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anshuit.kanbanhub.dtos.EmployeeDto;
import com.anshuit.kanbanhub.dtos.ProjectDto;
import com.anshuit.kanbanhub.dtos.TaskDto;
import com.anshuit.kanbanhub.entities.Employee;
import com.anshuit.kanbanhub.entities.Project;
import com.anshuit.kanbanhub.entities.Task;

@Service
public class DataTransferService {

	@Autowired
	private ModelMapper modelMapper;

	public EmployeeDto mapEmployeeToEmployeeDto(Employee employee) {
		return modelMapper.map(employee, EmployeeDto.class);
	}

	public Employee mapEmployeeDtoToEmployee(EmployeeDto employeeDto) {
		return modelMapper.map(employeeDto, Employee.class);
	}

	public ProjectDto mapProjectToProjectDto(Project project) {
		return modelMapper.map(project, ProjectDto.class);
	}

	public Project mapProjectDtoToProject(ProjectDto projectDto) {
		return modelMapper.map(projectDto, Project.class);
	}

	public TaskDto mapTaskToTaskDto(Task task) {
		return modelMapper.map(task, TaskDto.class);
	}

	public Task mapTaskDtoToTask(TaskDto taskDto) {
		return modelMapper.map(taskDto, Task.class);
	}
}
