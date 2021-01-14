package com.esprit.dto.request.modules;

import java.util.List;

public class AssignClassToModuleRequest {

	private int coefficient;

	private int nbrHours;

	private String semester;

	private List<String> periods;

	private String examType;

	private List<String> teacherIds;

	private String classId;

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	public int getNbrHours() {
		return nbrHours;
	}

	public void setNbrHours(int nbrHours) {
		this.nbrHours = nbrHours;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public List<String> getPeriods() {
		return periods;
	}

	public void setPeriods(List<String> periods) {
		this.periods = periods;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public List<String> getTeacherIds() {
		return teacherIds;
	}

	public void setTeacherIds(List<String> teacherIds) {
		this.teacherIds = teacherIds;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

}