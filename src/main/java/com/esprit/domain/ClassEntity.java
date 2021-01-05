package com.esprit.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ClassEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "class-uuid")
	@GenericGenerator(name = "class-uuid", strategy = "uuid2")
	private String classId;

	@Column(unique = true)
	private String code;

	private String label;

	private int nbrStudents;

	private int nbrGroups;

	@ManyToOne(optional = false)
	@JoinColumn(name = "speciality_id")
	private SpecialityEntity speciality;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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

	public SpecialityEntity getSpeciality() {
		return speciality;
	}

	public void setSpeciality(SpecialityEntity speciality) {
		this.speciality = speciality;
	}

}