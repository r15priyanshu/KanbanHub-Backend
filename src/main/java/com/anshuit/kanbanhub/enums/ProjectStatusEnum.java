package com.anshuit.kanbanhub.enums;

import lombok.Getter;

@Getter
public enum ProjectStatusEnum {

	CREATED(201, "CREATED", "TO BE ASSIGNED WHEN A NEW PROJECT IS CREATED !!"),
	IN_PROGRESS(202, "IN-PROGRESS", "TO BE ASSIGNED WHEN PROJECT HAS BEEN STARTED FOR WORK !!"),
	ON_HOLD(203, "ON-HOLD", "TO BE ASSIGNED WHEN PROJECT HAS BEEN STOPPED TEMPORARILY !!"),
	COMPLETED(204, "COMPLETED", "TO BE ASSIGNED WHEN A PROJECT HAS BEEN COMPLETED !!");

	private int projectStatusCode;
	private String projectStatusName;
	private String projectStatusDescription;

	private ProjectStatusEnum() {
	}

	private ProjectStatusEnum(int projectStatusCode, String projectStatusName, String projectStatusDescription) {
		this.projectStatusCode = projectStatusCode;
		this.projectStatusName = projectStatusName;
		this.projectStatusDescription = projectStatusDescription;
	}

	public static ProjectStatusEnum getEnumTypeFromTaskStatusName(String name) {
		for (ProjectStatusEnum status : values()) {
			if (status.getProjectStatusName().equals(name)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Unknown Project Status Name Passed : " + name);
	}
}
