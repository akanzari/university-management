package com.esprit.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.esprit.enums.ExamTypeEnum;
import com.esprit.enums.PeriodEnum;
import com.esprit.enums.SemesterEnum;

public class UpdateModuleRequest {

	@NotBlank
	private String moduleId;

	@NotNull
	private int nbrHours;

	@NotNull
	private int coefficient;

	@NotNull
	private SemesterEnum semester;

	@NotEmpty
	private List<PeriodEnum> periods;

	@NotNull
	private ExamTypeEnum examType;

	@NotBlank
	private String teacherId;

	@NotBlank
	private String classId;

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
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

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

}