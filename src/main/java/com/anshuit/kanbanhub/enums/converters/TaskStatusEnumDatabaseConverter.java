package com.anshuit.kanbanhub.enums.converters;

import com.anshuit.kanbanhub.enums.TaskStatusEnum;

import jakarta.persistence.AttributeConverter;

public class TaskStatusEnumDatabaseConverter implements AttributeConverter<TaskStatusEnum, String> {

	@Override
	public String convertToDatabaseColumn(TaskStatusEnum attribute) {
		// This will convert Enum Type to the desired type/value you want to store in DB
		return attribute.getTaskStatusName();
	}

	@Override
	public TaskStatusEnum convertToEntityAttribute(String dbData) {
		// This method will convert DB stored data back to its corresponding enum Type
		return TaskStatusEnum.getEnumTypeFromTaskStatusName(dbData);
	}
}
