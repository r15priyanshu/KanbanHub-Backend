package com.anshuit.kanbanhub.constants;

import java.util.List;

public class GlobalConstants {
	private GlobalConstants() {

	}

	public static final String DEFAULT_APPLICATION_NAME = "Kanban-Hub";
	public static final String DEFAULT_FRONTEND_APPLICATION_NAME = "Kanban-Hub Frontend";
	public static final String DEFAULT_FRONTEND_ORIGIN_URL = "http://localhost:4200";
	public static final String DEFAULT_ROLE_ONE = "ROLE_NORMAL";
	public static final String DEFAULT_ROLE_TWO = "ROLE_ADMIN";
	public static final String DEFAULT_PROJECT_ONE_NAME = "KANBAN-HUB FRONTEND";
	public static final String DEFAULT_PROJECT_ONE_DESCRIPTION = "Develop the user interface for Kanban Hub with features including task boards, real-time updates, and drag-and-drop functionality.";
	public static final String DEFAULT_PROJECT_TWO_NAME = "KANBAN-HUB BACKEND";
	public static final String DEFAULT_PROJECT_TWO_DESCRIPTION = "Build a scalable API and database system for Kanban Hub to manage tasks, users, and real-time updates securely and efficiently.";
	public static final String DEFAULT_PROFILE_PIC_NAME = "default.jpg";
	public static final String TASK_STATUS_UN_ASSIGNED = "UN-ASSIGNED";
	public static final String TASK_STATUS_IN_PROGRESS = "IN-PROGRESS";
	public static final String TASK_STATUS_COMPLETED = "COMPLETED";
	public static final long DEFAULT_TASK_DUE_DAYS_COUNT_MILLISECONDS = 3 * 24 * 60 * 60 * 1000;
	
	public static final String JWT_DEFAULT_SECRET = "MY-CUSTOM-SECRET";
	public static final String JWT_TOKEN_RESPONSE_HEADER_KEY = "Jwt-Token";
	public static final long JWT_TOKEN_VALIDITY_IN_MILLISECONDS = 8 * 60 * 60 * 1000; // HR,MIN,SEC,MILLI

	public static final List<String> ALLOWED_ORIGINS_LIST = List.of(DEFAULT_FRONTEND_ORIGIN_URL);
	public static final List<String> ALLOWED_HEADERS_LIST = List.of("*");
	public static final List<String> ALLOWED_METHODS_LIST = List.of("GET", "PUT", "POST", "DELETE", "OPTIONS");
	public static final List<String> EXPOSED_HEADERS_LIST = List.of("*");

	public static final String EMPLOYEE_NOT_FOUND_WITH_ID = "Employee not found with id : ";
	public static final String EMPLOYEE_NOT_FOUND_WITH_EMAIL = "Employee not found with id : ";
	public static final String EMPLOYEE_ALREADY_EXIST_WITH_EMAIL = "Employee already exist with email : ";
	public static final String EMPLOYEE_ALREADY_ALLOCATED_TO_PROJECT = "Employee already added in this project with email : ";
	public static final String PROJECT_NOT_FOUND_WITH_ID = "Project not found with id : ";
	public static final String TASK_NOT_FOUND_WITH_ID = "Task not found with id : ";

	public static final String LOGIN_URL = "/auth/login";
	public static final String REGISTER_URL = "/auth/register";

}
