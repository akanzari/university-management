package com.esprit.dto.classes;

import com.esprit.dto.SpecialityDTO;

public class ClassDTO {

	private String classId;

	private String label;

	private SpecialityDTO speciality;

	private String category;

	private String email;

	private int nbrStudents;

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

	public SpecialityDTO getSpeciality() {
		return speciality;
	}

	public void setSpeciality(SpecialityDTO speciality) {
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
