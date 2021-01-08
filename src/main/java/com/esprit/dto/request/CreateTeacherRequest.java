package com.esprit.dto.request;

import javax.validation.constraints.NotBlank;

public class CreateTeacherRequest {

	@NotBlank
	private String userId;

	@NotBlank
	private String phone;

	@NotBlank
	private String departmentId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

}