package com.esprit.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateClassRequest {

	@NotBlank
	private String classId;

	@NotBlank
	private String label;

	@NotNull
	private int nbrStudents;

	@NotNull
	private int nbrGroups;

	@NotBlank
	private String specialityId;

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

	public String getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(String specialityId) {
		this.specialityId = specialityId;
	}

}