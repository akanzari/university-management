package com.esprit.dto.response;

import java.util.List;

import com.esprit.enums.ExamTypeEnum;
import com.esprit.enums.PeriodEnum;
import com.esprit.enums.SemesterEnum;

public class ModuleResponse {
	
	private String moduleId;

	private String code;

	private String designation;

	private int nbrHours;

	private int coefficient;

	private SemesterEnum semester;

	private List<PeriodEnum> periods;

	private ExamTypeEnum examType;

	private TeacherResponse teacher;

	private ClassResponse classs;

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getNbrHours() {
		return nbrHours;
	}

	public void setNbrHours(int nbrHours) {
		this.nbrHours = nbrHours;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	public SemesterEnum getSemester() {
		return semester;
	}

	public void setSemester(SemesterEnum semester) {
		this.semester = semester;
	}

	public List<PeriodEnum> getPeriods() {
		return periods;
	}

	public void setPeriods(List<PeriodEnum> periods) {
		this.periods = periods;
	}

	public ExamTypeEnum getExamType() {
		return examType;
	}

	public void setExamType(ExamTypeEnum examType) {
		this.examType = examType;
	}

	public TeacherResponse getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherResponse teacher) {
		this.teacher = teacher;
	}

	public ClassResponse getClasss() {
		return classs;
	}

	public void setClasss(ClassResponse classs) {
		this.classs = classs;
	}

}