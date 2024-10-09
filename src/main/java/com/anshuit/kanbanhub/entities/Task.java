package com.anshuit.kanbanhub.entities;

import java.util.Date;

import com.anshuit.kanbanhub.constants.GlobalConstants;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence_generator")
	@SequenceGenerator(name = "task_sequence_generator", sequenceName = "task_sequence", allocationSize = 1)
	private int taskId;
	private String taskDisplayId;
	private String taskName;
	private String taskDescription;
	private String taskStatus;
	private Date startDate;
	private Date dueDate;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "employeeId")
	private Employee employee;

	@PrePersist
	private void assignTaskDisplayId() {
		this.taskDisplayId = GlobalConstants.DEFAULT_TASK_DISPLAY_ID_PREFIX + this.taskId;
	}
}
