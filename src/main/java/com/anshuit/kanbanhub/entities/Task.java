package com.anshuit.kanbanhub.entities;

import java.util.Date;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.enums.TaskStatusEnum;
import com.anshuit.kanbanhub.enums.converters.TaskStatusEnumDatabaseConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int taskId;
	private transient String taskDisplayId;
	private String taskName;
	private String taskDescription;

	@Convert(converter = TaskStatusEnumDatabaseConverter.class)
	private TaskStatusEnum taskStatus;
	private Date startDate;
	private Date dueDate;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "employeeId")
	private Employee employee;

	public String getTaskDisplayId() {
		this.taskDisplayId = GlobalConstants.DEFAULT_TASK_DISPLAY_ID_PREFIX + this.taskId;
		return taskDisplayId;
	}
}
