package com.anshuit.kanbanhub.entities;

import java.util.Date;

import jakarta.persistence.CascadeType;
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
	private String taskName;
	private String taskDescription;
	private String taskStatus;
	private Date startDate;
	private Date dueDate;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "employeeId")
	private Employee employee;
}
