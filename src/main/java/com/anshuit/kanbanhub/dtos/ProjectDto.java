package com.anshuit.kanbanhub.dtos;

import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDto {
	private int projectId;
	private String projectDisplayId;
	private String projectName;
	private String description;
	private Date startDate;
	private Date endDate;
	private String projectStatus;
	private Set<EmployeeDto> employees;
	private List<TaskDto> tasks;
}
