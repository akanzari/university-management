package com.esprit.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CLASSE")
public class ClassEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String classId;

	private String label;

	private int nbrStudents;

	@ManyToOne(optional = true)
	@JoinColumn(name = "fk_speciality")
	private SpecialityEntity speciality;

	private String category;

	private String email;

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

	public SpecialityEntity getSpeciality() {
		return speciality;
	}

	public void setSpeciality(SpecialityEntity speciality) {
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

}