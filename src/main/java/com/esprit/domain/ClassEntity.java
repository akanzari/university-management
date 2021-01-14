package com.esprit.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ClassEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String classId;

	private String label;

	private int nbrStudents;

	private int nbrGroups;

	private String speciality;

	private String category;

	private String email;

	@OneToMany(mappedBy = "classs", cascade = CascadeType.ALL)
	private List<AssignClassEntity> assignClasses;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getNbrStudents() {
		return nbrStudents;
	}

	public void setNbrStudents(int nbrStudents) {
		this.nbrStudents = nbrStudents;
	}

	public int getNbrGroups() {
		return nbrGroups;
	}

	public void setNbrGroups(int nbrGroups) {
		this.nbrGroups = nbrGroups;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<AssignClassEntity> getAssignClasses() {
		return assignClasses;
	}

	public void setAssignClasses(List<AssignClassEntity> assignClasses) {
		this.assignClasses = assignClasses;
	}

}