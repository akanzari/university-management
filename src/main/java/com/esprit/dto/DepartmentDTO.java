package com.esprit.dto;

import java.util.List;

public class DepartmentDTO {

	private String departmenetId;

	private String label;

	private List<String> up;

	public String getDepartmenetId() {
		return departmenetId;
	}

	public DepartmentDTO departmenetId(String departmenetId) {
		this.departmenetId = departmenetId;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public DepartmentDTO label(String label) {
		this.label = label;
		return this;
	}

	public List<String> getUp() {
		return up;
	}

	public DepartmentDTO up(List<String> up) {
		this.up = up;
		return this;
	}

}