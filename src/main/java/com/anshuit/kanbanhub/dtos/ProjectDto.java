package com.anshuit.kanbanhub.dtos;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.anshuit.kanbanhub.entities.Task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDto {
	private int projectId;
	private String projectName;
	private String description;
	private Date startDate;
	private Date endDate;
	private boolean statusActive;
	private Set<EmployeeDto> employees;
	private List<Task> tasks;
}
