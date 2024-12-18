package com.anshuit.kanbanhub.enums;

import java.util.Objects;

import com.anshuit.kanbanhub.constants.GlobalConstants;

public enum ExceptionDetailsEnum {

	// Employee Related Constants
	EMPLOYEE_NOT_FOUND_WITH_ID("1001", "Employee not found with id : %d"),

	EMPLOYEE_NOT_FOUND_WITH_DISPLAY_ID("1002", "Employee not found with display id : %s"),

	EMPLOYEE_NOT_FOUND_WITH_EMAIL("1003", "Employee not found with email : %s"),

	EMPLOYEE_ALREADY_EXIST_WITH_EMAIL("1004", "Employee already exist with email : %s"),

	EMPLOYEE_ALREADY_ALLOCATED_TO_PROJECT_WITH_EMAIL("1005",
			"Employee already allocated in this project with email : %s"),

	EMPLOYEE_DISPLAY_ID_NOT_STARTING_WITH_PREFIX("1006",
			"Employee display id not starting with : " + GlobalConstants.DEFAULT_EMPLOYEE_DISPLAY_ID_PREFIX),

	EMPLOYEE_ID_FROM_EMPLOYEE_DISPLAY_ID_PARSING_ERROR("1007",
			"Error while parsing employeeId from employeeDisplayId !!"),

	// Project Related Constants
	PROJECT_NOT_FOUND_WITH_ID("2001", "Project not found with id : %d"),

	PROJECT_NOT_FOUND_WITH_PROJECT_DISPLAY_ID("2002", "Project not found with project display id : %s"),

	PROJECT_DISPLAY_ID_NOT_STARTING_WITH_PREFIX("2003",
			"Project display id not starting with : " + GlobalConstants.DEFAULT_PROJECT_DISPLAY_ID_PREFIX),

	PROJECT_ID_FROM_PROJECT_DISPLAY_ID_PARSING_ERROR("2004", "Error while parsing projectId from projectDisplayId !!"),

	// Task Related Constants
	TASK_NOT_FOUND_WITH_ID("3001", "Task not found with id : %d"),

	TASK_NOT_FOUND_WITH_TASK_DISPLAY_ID("3002", "Task not found with task display id : %s"),

	TASK_DISPLAY_ID_NOT_STARTING_WITH_PREFIX("3003",
			"Task display id not starting with : " + GlobalConstants.DEFAULT_TASK_DISPLAY_ID_PREFIX),

	TASK_ID_FROM_TASK_DISPLAY_ID_PARSING_ERROR("3004", "Error while parsing taskId from taskDisplayId !!"),

	// JWT and Token Related Constants
	JWT_SIGNATURE_EXCEPTION_MESSAGE("4001",
			"JWT Signature Does Not Match Locally Computed signature !! Token Might Have Been Tampered !!"),

	JWT_MALFORMED_EXCEPTION_MESSAGE("4002", "Token Malformed !! Token Might Have Been Tampered !!"),

	JWT_EXPIRED_EXCEPTION_MESSAGE("4003", "Token Already Expired !!"),

	REFRESH_TOKEN_NOT_FOUND_WITH_ID("4004", "Refresh Token Not Found With Id : %d"),

	REFRESH_TOKEN_NOT_FOUND_WITH_TOKEN("4005", "Refresh Token Not Found With Token : %s"),

	REFRESH_TOKEN_EXPIRED_WITH_PERFORM_RE_LOGIN_MSG("4006", "Refresh Token Already Expired !! Please Re-Login !!"),

	// Other Constants
	PROFILE_PICTURE_NOT_PRESENT("5001", "Profile Picture Not Present !!"),

	ERROR_IN_UPDATING_PROFILE_PICTURE("5002", "Error In Uploading Profile Picture !!"),

	NOT_AN_ALLOWED_IMAGE_EXTENSION("5003", "Not An Allowed Image Extension !! Allowed Extensions Are : "
			+ GlobalConstants.ALLOWED_PROFILE_PICTURE_IMAGE_EXTENSIONS),;

	;

	private final String exceptionCode;
	private final String exceptionMessage;

	private ExceptionDetailsEnum(String exceptionCode, String exceptionMessage) {
		this.exceptionCode = exceptionCode;
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public static String getFormattedExceptionMessage(ExceptionDetailsEnum exceptionDetailsEnum, Object... args) {
		if (Objects.isNull(args) || args.length == 0)
			return exceptionDetailsEnum.getExceptionMessage();

		return String.format(exceptionDetailsEnum.getExceptionMessage(), args);
	}
}
