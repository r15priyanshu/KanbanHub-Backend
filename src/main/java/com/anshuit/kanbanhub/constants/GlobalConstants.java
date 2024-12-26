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

	public static final List<String> ALLOWED_ORIGINS_LIST = List.of(DEFAULT_FRONTEND_ORIGIN_URL);
	public static final List<String> ALLOWED_HEADERS_LIST = List.of("*");
	public static final List<String> ALLOWED_METHODS_LIST = List.of("GET", "PUT", "POST", "DELETE", "OPTIONS");
	public static final List<String> EXPOSED_HEADERS_LIST = List.of("*");

	public static final String LOGIN_URL = "/auth/login";
	public static final String REGISTER_URL = "/auth/register";
	public static final String CHECK_TOKEN_VALIDITY_URL = "/tokenAndRefreshToken/validateToken";
	public static final String REFRESH_TOKEN_BY_EMPLOYEE_DISPLAY_ID_URL = "/public/tokenAndRefreshToken/refreshToken/employee/{employeeDisplayId}";
	public static final String CHECK_REFRESH_TOKEN_VALIDITY_URL = "/public/tokenAndRefreshToken/validateRefreshToken";
	public static final String DELETE_REFRESH_TOKEN_BY_TOKEN_STRING_IN_REQUEST_BODY_URL = "/tokenAndRefreshToken/deleteRefreshToken";

	public static final String EXTENSION_JPG = ".jpg";
	public static final String EXTENSION_JPEG = ".jpeg";
	public static final String EXTENSION_PNG = ".png";
	public static final List<String> ALLOWED_PROFILE_PICTURE_IMAGE_EXTENSIONS = List.of(EXTENSION_JPG, EXTENSION_JPEG,
			EXTENSION_PNG);

	// KEY USED FOR MAPS or DBS
	public static final String KEY_EMPLOYEE_LCASE = "employee";

	// SET OF URLS FOR WHICH TOKEN VALIDATOR FILTER SHOULD NOT RUN
	public static final Set<String> EXCLUDED_PATHS_FOR_JWT_TOKEN_VALIDATOR_FILTER_SET = Set.of(
			GlobalConstants.LOGIN_URL, GlobalConstants.REGISTER_URL, GlobalConstants.CHECK_TOKEN_VALIDITY_URL,
			GlobalConstants.CHECK_REFRESH_TOKEN_VALIDITY_URL, GlobalConstants.REFRESH_TOKEN_BY_EMPLOYEE_DISPLAY_ID_URL,
			GlobalConstants.DELETE_REFRESH_TOKEN_BY_TOKEN_STRING_IN_REQUEST_BODY_URL);
}
