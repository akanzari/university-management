package com.esprit.dto.response;

import java.util.Set;

public class UserResponse {

	private String id;
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String status;
	
	private String depatement;;
	
	private String createdTimestamp;
	
	private Set<String> roles;

	public String getId() {
		return id;
	}

	public UserResponse id(String id) {
		this.id = id;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public UserResponse username(String username) {
		this.username = username;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public UserResponse firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public UserResponse lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserResponse email(String email) {
		this.email = email;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public UserResponse status(String status) {
		this.status = status;
		return this;
	}

	public String getDepatement() {
		return depatement;
	}

	public UserResponse depatement(String depatement) {
		this.depatement = depatement;
		return this;
	}

	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	public UserResponse createdTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
		return this;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public UserResponse roles(Set<String> roles) {
		this.roles = roles;
		return this;
	}

}