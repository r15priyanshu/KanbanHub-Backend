package com.anshuit.kanbanhub;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.entities.Address;
import com.anshuit.kanbanhub.entities.Employee;
import com.anshuit.kanbanhub.entities.Project;
import com.anshuit.kanbanhub.entities.Role;
import com.anshuit.kanbanhub.entities.Task;
import com.anshuit.kanbanhub.repositories.EmployeeRepository;
import com.anshuit.kanbanhub.repositories.RoleRepository;
import com.anshuit.kanbanhub.services.impls.EmployeeServiceImpl;
import com.anshuit.kanbanhub.services.impls.ProjectServiceImpl;
import com.anshuit.kanbanhub.services.impls.TaskServiceImpl;

@SpringBootApplication
public class KanbanHubApplication implements ApplicationRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeServiceImpl employeeService;

	@Autowired
	private ProjectServiceImpl projectService;

	@Autowired
	private TaskServiceImpl taskService;

	public static void main(String[] args) {
		SpringApplication.run(KanbanHubApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// CREATING 2 DEFAULT ROLES FOR THIS APPLICATION
		roleRepository.findById(1).orElseGet(() -> roleRepository.save(new Role(GlobalConstants.DEFAULT_ROLE_ONE)));
		roleRepository.findById(2).orElseGet(() -> roleRepository.save(new Role(GlobalConstants.DEFAULT_ROLE_TWO)));

		// CREATING 2 DEFAULT EMPLOYEES FOR THIS APPLICATION
		employeeService.getEmployeeByEmailOptional("anshu@gmail.com").orElseGet(() -> {
			Employee employee1 = new Employee();
			employee1.setFirstName("Anshu");
			employee1.setLastName("Anand");
			employee1.setEmail("anshu@gmail.com");
			employee1.setPassword("12345");
			employee1.setAddress(new Address("PATNA", "BIHAR"));
			Employee savedEmployee1 = employeeService.createEmployee(employee1);
			savedEmployee1.setRole(roleRepository.findById(1).get());
			return employeeRepository.save(savedEmployee1);
		});

		employeeService.getEmployeeByEmailOptional("shalu@gmail.com").orElseGet(() -> {
			Employee employee2 = new Employee();
			employee2.setFirstName("Shalu");
			employee2.setLastName("Kumari");
			employee2.setEmail("shalu@gmail.com");
			employee2.setPassword("12345");
			employee2.setAddress(new Address("RANCHI", "JHARKHAND"));
			Employee savedEmployee2 = employeeService.createEmployee(employee2);
			savedEmployee2.setRole(roleRepository.findById(2).get());
			return employeeRepository.save(savedEmployee2);
		});

		// CREATING 2 DEFAULT PROJECTS FOR THIS APPLICATION
		projectService.getProjectByIdOptional(1).orElseGet(() -> {
			Project project1 = new Project();
			project1.setProjectName(GlobalConstants.DEFAULT_PROJECT_ONE_NAME.toUpperCase());
			project1.setDescription(GlobalConstants.DEFAULT_PROJECT_ONE_DESCRIPTION);
			return projectService.createProject(project1);
		});
		projectService.getProjectByIdOptional(2).orElseGet(() -> {
			Project project2 = new Project();
			project2.setProjectName(GlobalConstants.DEFAULT_PROJECT_TWO_NAME.toUpperCase());
			project2.setDescription(GlobalConstants.DEFAULT_PROJECT_TWO_DESCRIPTION);
			return projectService.createProject(project2);
		});

		// CREATING 2 DEFAULT TASKS FOR THIS APPLICATION
		taskService.getTaskByIdOptional(1).orElseGet(() -> {
			Task task1 = new Task();
			task1.setTaskName("CREATE NAVBAR");
			task1.setTaskDescription("Create Navbar Having 5 links.");
			task1.setDueDate(
					new Date(System.currentTimeMillis() + GlobalConstants.DEFAULT_TASK_DUE_DAYS_COUNT_MILLISECONDS));
			return taskService.createTask(task1);
		});

		taskService.getTaskByIdOptional(2).orElseGet(() -> {
			Task task2 = new Task();
			task2.setTaskName("CREATE API");
			task2.setTaskDescription("Create Api To Fetch Images.");
			task2.setDueDate(
					new Date(System.currentTimeMillis() + GlobalConstants.DEFAULT_TASK_DUE_DAYS_COUNT_MILLISECONDS));
			return taskService.createTask(task2);
		});
	}

}
