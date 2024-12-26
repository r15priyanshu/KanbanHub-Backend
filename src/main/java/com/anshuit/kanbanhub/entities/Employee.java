package com.anshuit.kanbanhub.entities;

import com.anshuit.kanbanhub.constants.GlobalConstants;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;
	private transient String employeeDisplayId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String profilePic;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] profilePicData;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
	@JoinColumn(name = "address_id")
	private Address address;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private RefreshToken refreshToken;

	public Employee(String firstName, String lastName, String email, String password, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address = address;
	}

	public String getEmployeeDisplayId() {
		this.employeeDisplayId = GlobalConstants.DEFAULT_EMPLOYEE_DISPLAY_ID_PREFIX + this.employeeId;
		return this.employeeDisplayId;
	}
}
