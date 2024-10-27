package com.anshuit.kanbanhub.utils;

import org.springframework.stereotype.Component;

import com.anshuit.kanbanhub.constants.GlobalConstants;

@Component
public class CustomUtil {
	public boolean isImageHavingValidExtension(String filename) {
		filename = filename.toLowerCase();
		if (filename.endsWith(GlobalConstants.EXTENSION_PNG) || filename.endsWith(GlobalConstants.EXTENSION_JPG)
				|| filename.endsWith(GlobalConstants.EXTENSION_JPEG)) {
			return true;
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

	public int extractProjectIdFromProjectDisplayId(String projectDisplayId) {
		int projectId = Integer
				.parseInt(projectDisplayId.substring(GlobalConstants.DEFAULT_PROJECT_DISPLAY_ID_PREFIX.length()));
		return projectId;
	}

	public int extractTaskIdFromTaskDisplayId(String taskDisplayId) {
		int taskId = Integer.parseInt(taskDisplayId.substring(GlobalConstants.DEFAULT_TASK_DISPLAY_ID_PREFIX.length()));
		return taskId;
	}
}
