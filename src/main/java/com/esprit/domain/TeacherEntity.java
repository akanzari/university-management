package com.esprit.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TeacherEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String teacherId;

	private int cin;

	private String sex;

	@ElementCollection
	private List<String> phones;

	private String up;

	@ManyToOne(optional = true)
	@JoinColumn(name = "fk_departmenent")
	private DepartmentEntity departmenent;

	@JsonIgnore
	@ManyToMany(mappedBy = "teachers")
	private List<AssignClassEntity> assignClasses;

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

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public DepartmentEntity getDepartmenent() {
		return departmenent;
	}

	public void setDepartmenent(DepartmentEntity departmenent) {
		this.departmenent = departmenent;
	}

	public List<AssignClassEntity> getAssignClasses() {
		return assignClasses;
	}

	public void setAssignClasses(List<AssignClassEntity> assignClasses) {
		this.assignClasses = assignClasses;
	}

}