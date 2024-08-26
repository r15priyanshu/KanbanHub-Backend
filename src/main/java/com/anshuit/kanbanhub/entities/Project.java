package com.anshuit.kanbanhub.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int projectId;
	private String projectName;
	private String description;
	private Date startDate;
	private Date endDate;
	private boolean statusActive;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "employees_projects", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "projectId"), inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "employeeId"))
	private Set<Employee> employees = new HashSet<>();

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Task> tasks;
}