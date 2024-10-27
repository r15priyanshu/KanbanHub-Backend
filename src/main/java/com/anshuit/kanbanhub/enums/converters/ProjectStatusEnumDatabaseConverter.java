package com.anshuit.kanbanhub.enums.converters;

import com.anshuit.kanbanhub.enums.ProjectStatusEnum;

import jakarta.persistence.AttributeConverter;

public class ProjectStatusEnumDatabaseConverter implements AttributeConverter<ProjectStatusEnum, String> {

	@Override
	public String convertToDatabaseColumn(ProjectStatusEnum attribute) {
		// This will convert Enum Type to the desired type/value you want to store in DB
		return attribute.getProjectStatusName();
	}

	@Override
	public ProjectStatusEnum convertToEntityAttribute(String dbData) {
		// This method will convert DB stored data back to its corresponding enum Type
		return ProjectStatusEnum.getEnumTypeFromTaskStatusName(dbData);
	}
}
