package com.anshuit.kanbanhub.utils;

import org.springframework.stereotype.Component;

import com.anshuit.kanbanhub.constants.GlobalConstants;

@Component
public class CustomUtil {
	public boolean isImageHavingValidExtensionForProfilePicture(String filename) {
		filename = filename.toLowerCase();
		for (String extension : GlobalConstants.ALLOWED_PROFILE_PICTURE_IMAGE_EXTENSIONS) {
			if (filename.endsWith(extension)) {
				return true;
			}
		}
		return false;
	}

	public String getFileExtension(String filename) {
		if (filename == null || filename.isEmpty()) {
			return "";
		}

		int lastDotIndex = filename.lastIndexOf('.');
		if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
			return filename.substring(lastDotIndex);
		}

		return "";
	}
}
