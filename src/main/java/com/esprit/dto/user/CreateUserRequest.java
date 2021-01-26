package com.esprit.dto.user;

import javax.validation.constraints.NotBlank;

public class CreateUserRequest {

	private String userId;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private String email;

	@NotBlank
	private String role;

	private String username;

	private String password;

	private String pole;

	public String getFirstName() {
		return firstName;
	}

	public CreateUserRequest firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public CreateUserRequest lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public CreateUserRequest email(String email) {
		this.email = email;
		return this;
	}

	public String getRole() {
		return role;
	}

	public CreateUserRequest role(String role) {
		this.role = role;
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public CreateUserRequest userId(String userId) {
		this.userId = userId;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public CreateUserRequest username(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public CreateUserRequest password(String password) {
		this.password = password;
		return this;
	}

	public String getPole() {
		return pole;
	}

	public CreateUserRequest pole(String pole) {
		this.pole = pole;
		return this;
	}

}