package com.anshuit.kanbanhub.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
	private int taskId;
	private String taskDisplayId;
	private String taskName;
	private String taskDescription;
	private String taskStatus;
	private Date startDate;
	private Date dueDate;
	private EmployeeDto employee;
}
