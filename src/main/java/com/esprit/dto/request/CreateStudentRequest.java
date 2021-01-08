package com.esprit.dto.request;

import javax.validation.constraints.NotBlank;

public class CreateStudentRequest {

	@NotBlank
	private String userId;

	@NotBlank
	private String cin;

	@NotBlank
	private String classId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

}