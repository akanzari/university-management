package com.esprit.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.esprit.enums.ExamTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ESP_MODULE_CLASS")
public class AssignClassModuleEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "assign-uuid")
	@GenericGenerator(name = "assign-uuid", strategy = "uuid2")
	private String assignId;

	private int coefficient;

	private int nbrHour;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_semester")
	private SemesterEntity semester;

	@ManyToOne(optional = true)
	@JoinColumn(name = "fk_period")
	private PeriodEntity period;

	private ExamTypeEnum typeExam;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "ESP_MODULE_CLASS_ENSEIGNANT", joinColumns = @JoinColumn(name = "fk_assign_class"), inverseJoinColumns = @JoinColumn(name = "fk_teacher"))
	private List<TeacherEntity> teachers;

	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "fk_class")
	private ClassEntity classs;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_module")
	private ModuleEntity module;

	public String getAssignId() {
		return assignId;
	}

	public AssignClassModuleEntity assignId(String assignId) {
		this.assignId = assignId;
		return this;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public AssignClassModuleEntity coefficient(int coefficient) {
		this.coefficient = coefficient;
		return this;
	}

	public int getNbrHour() {
		return nbrHour;
	}

	public AssignClassModuleEntity nbrHour(int nbrHour) {
		this.nbrHour = nbrHour;
		return this;
	}

	public SemesterEntity getSemester() {
		return semester;
	}

	public AssignClassModuleEntity semester(SemesterEntity semester) {
		this.semester = semester;
		return this;
	}

	public PeriodEntity getPeriod() {
		return period;
	}

	public AssignClassModuleEntity period(PeriodEntity period) {
		this.period = period;
		return this;
	}

	public ExamTypeEnum getTypeExam() {
		return typeExam;
	}

	public AssignClassModuleEntity typeExam(ExamTypeEnum typeExam) {
		this.typeExam = typeExam;
		return this;
	}

	public List<TeacherEntity> getTeachers() {
		return teachers;
	}

	public AssignClassModuleEntity teachers(List<TeacherEntity> teachers) {
		this.teachers = teachers;
		return this;
	}

	public ClassEntity getClasss() {
		return classs;
	}

	public AssignClassModuleEntity classs(ClassEntity classs) {
		this.classs = classs;
		return this;
	}

	public ModuleEntity getModule() {
		return module;
	}

	public AssignClassModuleEntity module(ModuleEntity module) {
		this.module = module;
		return this;
	}

}