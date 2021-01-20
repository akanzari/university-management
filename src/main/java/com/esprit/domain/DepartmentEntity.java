package com.esprit.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEPARTMENT")
public class DepartmentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String departmenetId;

	private String label;

	@ElementCollection
	@CollectionTable(name = "DEPARTMENT_UP")
	private List<String> up;

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

	public List<String> getUp() {
		return up;
	}

	public void setUp(List<String> up) {
		this.up = up;
	}

}