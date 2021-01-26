package com.esprit.dto.teacher;

import java.util.List;

import com.esprit.dto.DepartmentDTO;
import com.esprit.dto.room.DisponibilityDTO;

public class TeacherDTO {

	private String teacherId;

	private String fullName;

	private String email;

	private String phone;

	private String up;

	private DepartmentDTO departement;

	private List<DisponibilityDTO> disponibilities;

	public String getTeacherId() {
		return teacherId;
	}

	public TeacherDTO teacherId(String teacherId) {
		this.teacherId = teacherId;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public TeacherDTO fullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public TeacherDTO email(String email) {
		this.email = email;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public TeacherDTO phone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getUp() {
		return up;
	}

	public TeacherDTO up(String up) {
		this.up = up;
		return this;
	}

	public DepartmentDTO getDepartement() {
		return departement;
	}

	public TeacherDTO departement(DepartmentDTO departement) {
		this.departement = departement;
		return this;
	}

	public List<DisponibilityDTO> getDisponibilities() {
		return disponibilities;
	}

	public TeacherDTO disponibilities(List<DisponibilityDTO> disponibilities) {
		this.disponibilities = disponibilities;
		return this;
	}

}