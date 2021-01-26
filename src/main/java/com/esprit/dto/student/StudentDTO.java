package com.esprit.dto.student;

import com.esprit.dto.classes.ClassDTO;

public class StudentDTO {

	private String studentId;

	private String fullName;

	private int cin;

	private String email;

	private ClassDTO classs;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
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

	public ClassDTO getClasss() {
		return classs;
	}

	public void setClasss(ClassDTO classs) {
		this.classs = classs;
	}

	public int getCin() {
		return cin;
	}

	public void setCin(int cin) {
		this.cin = cin;
	}

}