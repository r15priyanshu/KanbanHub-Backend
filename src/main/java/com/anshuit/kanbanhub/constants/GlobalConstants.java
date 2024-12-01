package com.anshuit.kanbanhub.constants;

import java.util.List;
import java.util.Set;

public class GlobalConstants {
	private GlobalConstants() {
	}

	public static final String DEFAULT_EMPLOYEE_DISPLAY_ID_PREFIX = "EMP";
	public static final String DEFAULT_APPLICATION_NAME = "Kanban-Hub";
	public static final String DEFAULT_FRONTEND_APPLICATION_NAME = "Kanban-Hub Frontend";
	public static final String DEFAULT_FRONTEND_ORIGIN_URL = "http://localhost:4200";
	public static final String DEFAULT_ROLE_ONE = "ROLE_NORMAL";
	public static final String DEFAULT_ROLE_TWO = "ROLE_ADMIN";
	public static final String DEFAULT_PROJECT_DISPLAY_ID_PREFIX = "KBH";
	public static final String DEFAULT_PROJECT_ONE_NAME = "KANBAN-HUB FRONTEND";
	public static final String DEFAULT_PROJECT_ONE_DESCRIPTION = "Develop the user interface for Kanban Hub with features including task boards, real-time updates, and drag-and-drop functionality.";
	public static final String DEFAULT_PROJECT_TWO_NAME = "KANBAN-HUB BACKEND";
	public static final String DEFAULT_PROJECT_TWO_DESCRIPTION = "Build a scalable API and database system for Kanban Hub to manage tasks, users, and real-time updates securely and efficiently.";
	public static final String DEFAULT_PROFILE_PIC_NAME = "default.jpg";
	public static final String DEFAULT_TASK_DISPLAY_ID_PREFIX = "TASK";
	public static final long DEFAULT_TASK_DUE_DAYS_COUNT_MILLISECONDS = 3 * 24 * 60 * 60 * 1000;

	public static final String JWT_DEFAULT_SECRET = "MY-CUSTOM-SECRET";
	public static final String JWT_TOKEN_RESPONSE_HEADER_KEY = "Jwt-Token";
	public static final String JWT_REFRESH_TOKEN_RESPONSE_HEADER_KEY = "Jwt-Refresh-Token";
	public static final long JWT_TOKEN_VALIDITY_IN_MILLISECONDS = 1 * 60 * 60 * 1000; // HR,MIN,SEC,MILLI
	public static final long JWT_REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS = 2 * 60 * 60 * 1000; // HR,MIN,SEC,MILLI
	public static final String JWT_SIGNATURE_EXCEPTION_MESSAGE = "JWT Signature Does Not Match Locally Computed signature !! Token Might Have Been Tampered !!";
	public static final String JWT_MALFORMED_EXCEPTION_MESSAGE = "Token Malformed !! Token Might Have Been Tampered !!";
	public static final String JWT_EXPIRED_EXCEPTION_MESSAGE = "Token Already Expired !!";

	public static final List<String> ALLOWED_ORIGINS_LIST = List.of(DEFAULT_FRONTEND_ORIGIN_URL);
	public static final List<String> ALLOWED_HEADERS_LIST = List.of("*");
	public static final List<String> ALLOWED_METHODS_LIST = List.of("GET", "PUT", "POST", "DELETE", "OPTIONS");
	public static final List<String> EXPOSED_HEADERS_LIST = List.of("*");

	public static final String EMPLOYEE_NOT_FOUND_WITH_ID = "Employee not found with id : ";
	public static final String EMPLOYEE_NOT_FOUND_WITH_DISPLAY_ID = "Employee not found with display id : ";
	public static final String EMPLOYEE_NOT_FOUND_WITH_EMAIL = "Employee not found with id : ";
	public static final String EMPLOYEE_ALREADY_EXIST_WITH_EMAIL = "Employee already exist with email : ";
	public static final String EMPLOYEE_ALREADY_ALLOCATED_TO_PROJECT = "Employee already added in this project with email : ";
	public static final String EMPLOYEE_DISPLAY_ID_NOT_STARTING_WITH_PREFIX = "Employee display id not starting with : "
			+ DEFAULT_EMPLOYEE_DISPLAY_ID_PREFIX;
	public static final String EMPLOYEE_ID_FROM_EMPLOYEE_DISPLAY_ID_PARSING_ERROR = "Error while parsing employeeId from employeeDisplayId";
	public static final String PROFILE_PICTURE_SUCCESSFULLY_UPDATED = "Profile Picture Successfully Updated !!";
	public static final String PROFILE_PICTURE_SUCCESSFULLY_REMOVED = "Profile Picture Successfully Removed !!";
	public static final String PROFILE_PICTURE_NOT_PRESENT = "Profile Picture Not Present !!";
	public static final String ERROR_IN_UPDATING_PROFILE_PICTURE = "Error In Uploading Profile Picture !!";
	public static final String NOT_AN_ALLOWED_IMAGE_EXTENSION = "Not An Allowed Image Extension !!";
	public static final String REFRESH_TOKEN_NOT_FOUND_WITH_ID = "Refresh Token Not Found With Id : ";
	public static final String REFRESH_TOKEN_NOT_FOUND_WITH_TOKEN = "Refresh Token Not Found With Token : ";
	public static final String REFRESH_TOKEN_SUCCESSFULLY_DELETED = "Refresh Token Successfully Deleted !!";
	public static final String REFRESH_TOKEN_EXPIRED_WITH_PERFORM_RE_LOGIN_MSG = "Refresh Token Already Expired !! Please Re-Login !!";

	public static final String PROJECT_NOT_FOUND_WITH_ID = "Project not found with id : ";
	public static final String PROJECT_NOT_FOUND_WITH_PROJECT_DISPLAY_ID = "Project not found with project display id : ";
	public static final String PROJECT_DISPLAY_ID_NOT_STARTING_WITH_PREFIX = "Project display id not starting with : "
			+ DEFAULT_PROJECT_DISPLAY_ID_PREFIX;
	public static final String PROJECT_ID_FROM_PROJECT_DISPLAY_ID_PARSING_ERROR = "Error while parsing projectId from projectDisplayId";
	public static final String TASK_NOT_FOUND_WITH_ID = "Task not found with id : ";
	public static final String TASK_NOT_FOUND_WITH_TASK_DISPLAY_ID = "Task not found with task display id : ";
	public static final String TASK_DISPLAY_ID_NOT_STARTING_WITH_PREFIX = "Task display id not starting with : "
			+ DEFAULT_TASK_DISPLAY_ID_PREFIX;
	public static final String TASK_ID_FROM_TASK_DISPLAY_ID_PARSING_ERROR = "Error while parsing taskId from taskDisplayId";

	public static final String LOGIN_URL = "/auth/login";
	public static final String REGISTER_URL = "/auth/register";
	public static final String CHECK_TOKEN_VALIDITY_URL = "/tokenAndRefreshToken/validateToken";
	public static final String REFRESH_TOKEN_URL = "/tokenAndRefreshToken/refreshToken";
	public static final String CHECK_REFRESH_TOKEN_VALIDITY_URL = "/tokenAndRefreshToken/validateRefreshToken";
	public static final String DELETE_REFRESH_TOKEN_BY_TOKEN_STRING_IN_REQUEST_BODY_URL = "/tokenAndRefreshToken/deleteRefreshToken";

	public static final String EXTENSION_JPG = ".jpg";
	public static final String EXTENSION_JPEG = ".jpeg";
	public static final String EXTENSION_PNG = ".png";

	// KEY USED FOR MAPS or DBS
	public static final String KEY_EMPLOYEE_LCASE = "employee";

	// SET OF URLS FOR WHICH TOKEN VALIDATOR FILTER SHOULD NOT RUN
	public static final Set<String> EXCLUDED_PATHS_FOR_JWT_TOKEN_VALIDATOR_FILTER_SET = Set.of(
			GlobalConstants.LOGIN_URL, GlobalConstants.REGISTER_URL, GlobalConstants.CHECK_TOKEN_VALIDITY_URL,
			GlobalConstants.CHECK_REFRESH_TOKEN_VALIDITY_URL, GlobalConstants.REFRESH_TOKEN_URL,
			GlobalConstants.DELETE_REFRESH_TOKEN_BY_TOKEN_STRING_IN_REQUEST_BODY_URL);
}
