package com.esprit.dto.response;

import com.esprit.dto.ClassDTO;

public class StudentResponse {

	private String userId;

	private String fullName;

	private String cin;

	private ClassDTO classs;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public ClassDTO getClasss() {
		return classs;
	}

	public void setClasss(ClassDTO classs) {
		this.classs = classs;
	}

}