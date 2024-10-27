package com.anshuit.kanbanhub.services.impls;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.entities.Project;
import com.anshuit.kanbanhub.entities.Task;
import com.anshuit.kanbanhub.exceptions.CustomException;
import com.anshuit.kanbanhub.repositories.ProjectRepository;
import com.anshuit.kanbanhub.utils.CustomUtil;

@Service
public class ProjectServiceImpl {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private CustomUtil customUtil;

	public Project createProject(Project project) {
		project.setStartDate(new Date());
		project.setStatusActive(true);
		return projectRepository.save(project);
	}

	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	public Project getProjectById(int projectId) {
		Project project = projectRepository.findById(projectId).orElseThrow(
				() -> new CustomException(GlobalConstants.PROJECT_NOT_FOUND_WITH_ID + projectId, HttpStatus.NOT_FOUND));
		LinkedHashSet<Task> sortedSet = project.getTasks().stream()
				.sorted((task1, task2) -> -Integer.compare(task1.getTaskId(), task2.getTaskId())) // Sort by taskId
				.collect(Collectors.toCollection(LinkedHashSet::new));
		project.setTasks(sortedSet);
		return project;
	}

	public Project getProjectByProjectDisplayId(String projectDisplayId) {
		int projectId = customUtil.extractProjectIdFromProjectDisplayId(projectDisplayId);
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new CustomException(
						GlobalConstants.PROJECT_NOT_FOUND_WITH_PROJECT_DISPLAY_ID + projectDisplayId,
						HttpStatus.NOT_FOUND));
		LinkedHashSet<Task> sortedSet = project.getTasks().stream()
				.sorted((task1, task2) -> -Integer.compare(task1.getTaskId(), task2.getTaskId())) // Sort by taskId
				.collect(Collectors.toCollection(LinkedHashSet::new));
		project.setTasks(sortedSet);
		return project;
	}

	public Optional<Project> getProjectByIdOptional(int projectId) {
		return projectRepository.findById(projectId);
	}
}
