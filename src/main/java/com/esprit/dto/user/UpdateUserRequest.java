package com.esprit.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UpdateUserRequest {

	@NotBlank
	private String id;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String role;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}