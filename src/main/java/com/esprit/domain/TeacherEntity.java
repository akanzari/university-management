package com.esprit.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ESP_ENSEIGNANT")
public class TeacherEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String teacherId;

	private int cin;

	private String sex;

	@ElementCollection
	@CollectionTable(name = "ESP_ENSEIGNANT_PHONES")
	private List<String> phones;

	private String up;

	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "fk_departmenent")
	private DepartmentEntity departement;

	@JsonIgnore
	@ManyToMany(mappedBy = "teachers")
	private List<AssignClassModuleEntity> assignClasses;

	public String getTeacherId() {
		return teacherId;
	}

	public TeacherEntity teacherId(String teacherId) {
		this.teacherId = teacherId;
		return this;
	}

	public int getCin() {
		return cin;
	}

	public TeacherEntity cin(int cin) {
		this.cin = cin;
		return this;
	}

	public String getSex() {
		return sex;
	}

	public TeacherEntity sex(String sex) {
		this.sex = sex;
		return this;
	}

	public List<String> getPhones() {
		return phones;
	}

	public TeacherEntity phones(List<String> phones) {
		this.phones = phones;
		return this;
	}

	public String getUp() {
		return up;
	}

	public TeacherEntity up(String up) {
		this.up = up;
		return this;
	}

	public DepartmentEntity getDepartement() {
		return departement;
	}

	public TeacherEntity departement(DepartmentEntity departement) {
		this.departement = departement;
		return this;
	}

	public List<AssignClassModuleEntity> getAssignClasses() {
		return assignClasses;
	}

	public TeacherEntity assignClasses(List<AssignClassModuleEntity> assignClasses) {
		this.assignClasses = assignClasses;
		return this;
	}

}