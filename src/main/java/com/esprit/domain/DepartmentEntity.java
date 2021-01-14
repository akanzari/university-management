package com.esprit.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DepartmentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String departmenetId;

	private String label;

	public String getDepartmenetId() {
		return departmenetId;
	}

	public void setDepartmenetId(String departmenetId) {
		this.departmenetId = departmenetId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}