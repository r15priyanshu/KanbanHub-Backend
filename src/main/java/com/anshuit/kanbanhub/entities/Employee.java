package com.anshuit.kanbanhub.entities;

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

	public Employee(String firstName, String lastName, String email, String password, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address = address;
	}
}
