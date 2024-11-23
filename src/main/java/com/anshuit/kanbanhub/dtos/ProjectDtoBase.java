package com.anshuit.kanbanhub.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDtoBase {
	private int projectId;
	private String projectDisplayId;
	private String projectName;
	private String description;
	private Date createdDate;
	private Date startDate;
	private Date endDate;
	private String projectStatus;
}
