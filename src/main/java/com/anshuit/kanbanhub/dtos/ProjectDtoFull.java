package com.anshuit.kanbanhub.dtos;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDtoFull extends ProjectDtoBase {
	private Set<EmployeeDto> employees;
	private List<TaskDto> tasks;
}
