package com.esprit.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DEPARTMENT")
public class DepartmentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "departmenet-uuid")
	@GenericGenerator(name = "departmenet-uuid", strategy = "uuid2")
	private String departmenetId;

	private String code;

	private String label;

	public String getDepartmenetId() {
		return departmenetId;
	}

	public void setDepartmenetId(String departmenetId) {
		this.departmenetId = departmenetId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}