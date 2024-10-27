package com.anshuit.kanbanhub.enums;

import lombok.Getter;

@Getter
public enum TaskStatusEnum {

	UN_ASSIGNED(101, "UN-ASSIGNED", "TO BE ASSIGNED WHEN A NEW TASK IS CREATED !!"),
	IN_PROGRESS(102, "IN-PROGRESS", "TO BE ASSIGNED WHEN A TASK HAS BEEN PICKED UP FOR WORK !!"),
	COMPLETED(103, "COMPLETED", "TO BE ASSIGNED WHEN A TASK HAS BEEN COMPLETED !!");

	private int taskStatusCode;
	private String taskStatusName;
	private String taskStatusDescription;

	private TaskStatusEnum() {
	}

	private TaskStatusEnum(int taskStatusCode, String taskStatusName, String taskStatusDescription) {
		this.taskStatusCode = taskStatusCode;
		this.taskStatusName = taskStatusName;
		this.taskStatusDescription = taskStatusDescription;
	}

	public static TaskStatusEnum getEnumTypeFromTaskStatusName(String name) {
		for (TaskStatusEnum status : values()) {
			if (status.getTaskStatusName().equals(name)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Unknown Task Status Name Passed : " + name);
	}
}
