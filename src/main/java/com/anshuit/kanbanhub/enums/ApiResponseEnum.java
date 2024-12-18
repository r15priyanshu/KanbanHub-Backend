package com.anshuit.kanbanhub.enums;

public enum ApiResponseEnum {
	PROFILE_PICTURE_SUCCESSFULLY_UPDATED("Profile Picture Successfully Updated !!"),
	PROFILE_PICTURE_SUCCESSFULLY_REMOVED("Profile Picture Successfully Removed !!"),
	REFRESH_TOKEN_SUCCESSFULLY_DELETED("Refresh Token Successfully Deleted !!");

	private final String message;

	private ApiResponseEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
