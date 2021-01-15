package com.esprit.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.esprit.enums.DsexEnum;
import com.esprit.enums.ExamTypeEnum;
import com.esprit.enums.SemesterEnum;
import com.esprit.enums.SessionEnum;

@Entity
@Table(name = "EPREUVE")
public class ExamEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "exam-uuid")
	@GenericGenerator(name = "exam-uuid", strategy = "uuid2")
	private String examId;

	private Date examDate;

	private int examHour;

	private int examDuration;

	private ExamTypeEnum examType;

	private SemesterEnum semester;

	private SessionEnum examSession;

	private DsexEnum dsex;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_class")
	private ClassEntity classs;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_module")
	private ModuleEntity module;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_romm")
	private RoomEntity classRoom;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_supervisor")
	private TeacherEntity supervisor;

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public int getExamHour() {
		return examHour;
	}

	public void setExamHour(int examHour) {
		this.examHour = examHour;
	}

	public int getExamDuration() {
		return examDuration;
	}

	public void setExamDuration(int examDuration) {
		this.examDuration = examDuration;
	}

	public ExamTypeEnum getExamType() {
		return examType;
	}

	public void setExamType(ExamTypeEnum examType) {
		this.examType = examType;
	}

	public SemesterEnum getSemester() {
		return semester;
	}

	public void setSemester(SemesterEnum semester) {
		this.semester = semester;
	}

	public SessionEnum getExamSession() {
		return examSession;
	}

	public void setExamSession(SessionEnum examSession) {
		this.examSession = examSession;
	}

	public DsexEnum getDsex() {
		return dsex;
	}

	public void setDsex(DsexEnum dsex) {
		this.dsex = dsex;
	}

	public ClassEntity getClasss() {
		return classs;
	}

	public void setClasss(ClassEntity classs) {
		this.classs = classs;
	}

	public ModuleEntity getModule() {
		return module;
	}

	public void setModule(ModuleEntity module) {
		this.module = module;
	}

	public RoomEntity getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(RoomEntity classRoom) {
		this.classRoom = classRoom;
	}

	public TeacherEntity getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(TeacherEntity supervisor) {
		this.supervisor = supervisor;
	}

}