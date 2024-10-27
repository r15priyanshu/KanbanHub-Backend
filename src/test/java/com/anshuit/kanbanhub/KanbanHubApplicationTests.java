package com.anshuit.kanbanhub;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.anshuit.kanbanhub.dtos.TaskDto;
import com.anshuit.kanbanhub.entities.Employee;
import com.anshuit.kanbanhub.entities.Project;
import com.anshuit.kanbanhub.entities.Task;
import com.anshuit.kanbanhub.repositories.EmployeeRepository;
import com.anshuit.kanbanhub.repositories.ProjectRepository;

@SpringBootTest
class KanbanHubApplicationTests {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ProjectRepository projectRepository;

	// @Test
	void addEmployeeToProject() {
		Employee employee = employeeRepository.findById(1).get();
		Project project = projectRepository.findById(1).get();
		project.getEmployees().add(employee);
		Project saved = projectRepository.save(project);
		System.out.println(saved);
	}

	// @Test
	void testDtoToEntityMapping() {
		TaskDto taskDto = new TaskDto();
		taskDto.setTaskStatus("UN-ASSIGNED");

		Task task = modelMapper.map(taskDto, Task.class);
		System.out.println(task);
	}
}
