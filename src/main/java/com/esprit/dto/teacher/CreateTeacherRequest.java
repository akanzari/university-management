package com.esprit.dto.teacher;

import java.util.List;

import com.esprit.dto.CreateDisponibilityRequest;

public class CreateTeacherRequest {

	private String teacherId;

	private String fullName;

	private String email;

	private String phone;

	private String up;

	private String departement;

	private List<CreateDisponibilityRequest> disponibilities;

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public List<CreateDisponibilityRequest> getDisponibilities() {
		return disponibilities;
	}

	public void setDisponibilities(List<CreateDisponibilityRequest> disponibilities) {
		this.disponibilities = disponibilities;
	}

}