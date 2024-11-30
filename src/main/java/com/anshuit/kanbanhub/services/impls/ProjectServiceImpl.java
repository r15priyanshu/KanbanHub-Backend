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
import com.anshuit.kanbanhub.constants.JPAConstants;
import com.anshuit.kanbanhub.entities.Project;
import com.anshuit.kanbanhub.entities.Task;
import com.anshuit.kanbanhub.enums.ProjectStatusEnum;
import com.anshuit.kanbanhub.exceptions.CustomException;
import com.anshuit.kanbanhub.repositories.ProjectRepository;

@Service
public class ProjectServiceImpl {

	@Autowired
	private ProjectRepository projectRepository;

	public Project save(Project project) {
		return projectRepository.save(project);
	}

	public Project createProject(Project project) {
		project.setCreatedDate(new Date());
		project.setProjectStatus(ProjectStatusEnum.CREATED);
		return projectRepository.save(project);
	}

	public List<Project> getAllProjects() {
		return projectRepository.findAll(JPAConstants.SORT_PROJECT_BY_ID_DESC);
	}

	public List<Project> getAllProjectsPartial() {
		return projectRepository.findAllPartial(JPAConstants.SORT_PROJECT_BY_ID_DESC);
	}

	public Project getProjectByProjectDisplayId(String projectDisplayId) {
		int projectId = this.extractProjectIdFromProjectDisplayId(projectDisplayId);
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

	public int extractProjectIdFromProjectDisplayId(String projectDisplayId) {
		if (!projectDisplayId.startsWith(GlobalConstants.DEFAULT_PROJECT_DISPLAY_ID_PREFIX)) {
			throw new CustomException(GlobalConstants.PROJECT_DISPLAY_ID_NOT_STARTING_WITH_PREFIX,
					HttpStatus.BAD_REQUEST);
		}

		try {
			return Integer
					.parseInt(projectDisplayId.substring(GlobalConstants.DEFAULT_PROJECT_DISPLAY_ID_PREFIX.length()));
		} catch (NumberFormatException e) {
			throw new CustomException(GlobalConstants.PROJECT_ID_FROM_PROJECT_DISPLAY_ID_PARSING_ERROR,
					HttpStatus.BAD_REQUEST);
		}
	}
}
