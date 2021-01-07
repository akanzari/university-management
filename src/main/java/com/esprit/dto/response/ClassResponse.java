package com.esprit.dto.response;

import java.time.LocalDateTime;

public class ClassResponse {

	private String classId;

	private String code;

	private String label;

	private int nbrStudents;

	private int nbrGroups;

	private SpecialityResponse speciality;
	
	protected LocalDateTime createdDate;

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

	public SpecialityResponse getSpeciality() {
		return speciality;
	}

	public void setSpeciality(SpecialityResponse speciality) {
		this.speciality = speciality;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

}