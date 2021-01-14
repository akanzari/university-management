package com.esprit.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.esprit.enums.ExamTypeEnum;
import com.esprit.enums.PeriodEnum;
import com.esprit.enums.SemesterEnum;

@Entity
public class AssignClassEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "assign-uuid")
	@GenericGenerator(name = "assign-uuid", strategy = "uuid2")
	private String assignId;

	private int coefficient;

	private int nbrHours;

	private SemesterEnum semester;

	@ElementCollection(targetClass = PeriodEnum.class)
	private List<PeriodEnum> periods;

	private ExamTypeEnum examType;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "assign_class_teacher", joinColumns = @JoinColumn(name = "fk_assign_class"), inverseJoinColumns = @JoinColumn(name = "fk_teacher"))
	private List<TeacherEntity> teachers;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_class")
	private ClassEntity classs;

	public String getAssignId() {
		return assignId;
	}

	public AssignClassEntity assignId(String assignId) {
		this.assignId = assignId;
		return this;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public AssignClassEntity coefficient(int coefficient) {
		this.coefficient = coefficient;
		return this;
	}

	public int getNbrHours() {
		return nbrHours;
	}

	public AssignClassEntity nbrHours(int nbrHours) {
		this.nbrHours = nbrHours;
		return this;
	}

	public SemesterEnum getSemester() {
		return semester;
	}

	public AssignClassEntity semester(SemesterEnum semester) {
		this.semester = semester;
		return this;
	}

	public List<PeriodEnum> getPeriods() {
		return periods;
	}

	public AssignClassEntity periods(List<PeriodEnum> periods) {
		this.periods = periods;
		return this;
	}

	public ExamTypeEnum getExamType() {
		return examType;
	}

	public AssignClassEntity examType(ExamTypeEnum examType) {
		this.examType = examType;
		return this;
	}

	public List<TeacherEntity> getTeachers() {
		return teachers;
	}

	public AssignClassEntity teachers(List<TeacherEntity> teachers) {
		this.teachers = teachers;
		return this;
	}

	public ClassEntity getClasss() {
		return classs;
	}

	public AssignClassEntity classs(ClassEntity classs) {
		this.classs = classs;
		return this;
	}

}