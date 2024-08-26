package com.anshuit.kanbanhub;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.anshuit.kanbanhub.entities.Employee;
import com.anshuit.kanbanhub.entities.Project;
import com.anshuit.kanbanhub.repositories.EmployeeRepository;
import com.anshuit.kanbanhub.repositories.ProjectRepository;

@SpringBootTest
class KanbanHubApplicationTests {

	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	//@Test
	void addEmployeeToProject() {
		Employee employee = employeeRepository.findById(1).get();
		Project project = projectRepository.findById(1).get();
		project.getEmployees().add(employee);
		Project saved = projectRepository.save(project);
		System.out.println(saved);
	}

}
