package com.esprit.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.esprit.enums.ExamTypeEnum;
import com.esprit.enums.PeriodEnum;
import com.esprit.enums.SemesterEnum;

@Entity
public class ModuleEntity extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "module-uuid")
	@GenericGenerator(name = "module-uuid", strategy = "uuid2")
	private String moduleId;

	@Column(unique = true)
	private String code;

	private String designation;

	private int nbrHours;

	private int coefficient;

	private SemesterEnum semester;

	@ElementCollection(targetClass = PeriodEnum.class)
	private List<PeriodEnum> periods;

	private ExamTypeEnum examType;

	@ManyToOne(optional = false)
	@JoinColumn(name = "teacher_id")
	private TeacherEntity teacher;

	@ManyToOne(optional = false)
	@JoinColumn(name = "class_id")
	private ClassEntity classs;

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

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}

	public ClassEntity getClasss() {
		return classs;
	}

	public void setClasss(ClassEntity classs) {
		this.classs = classs;
	}

}