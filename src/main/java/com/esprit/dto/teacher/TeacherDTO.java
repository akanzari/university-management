package com.esprit.dto.teacher;

import java.util.List;

import com.esprit.dto.DepartmentDTO;

public class TeacherDTO {

	private String teacherId;

	private int cin;

	private String sex;

	private String fullName;

	private String email;

	private String phone;

	private String up;

	private DepartmentDTO departement;

	private List<DisponibilityTeacherDTO> disponibilityTeachers;

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public int getCin() {
		return cin;
	}

	public void setCin(int cin) {
		this.cin = cin;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public DepartmentDTO getDepartement() {
		return departement;
	}

	public void setDepartement(DepartmentDTO departement) {
		this.departement = departement;
	}

	public List<DisponibilityTeacherDTO> getDisponibilityTeachers() {
		return disponibilityTeachers;
	}

	public void setDisponibilityTeachers(List<DisponibilityTeacherDTO> disponibilityTeachers) {
		this.disponibilityTeachers = disponibilityTeachers;
	}

}