package com.esprit.dto.response;

public class StudentResponse {

	private String userId;

	private String cin;

	private ClassResponse classs;

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

	public ClassResponse getClasss() {
		return classs;
	}

	public void setClasss(ClassResponse classs) {
		this.classs = classs;
	}

}