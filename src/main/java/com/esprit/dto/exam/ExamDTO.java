package com.esprit.dto.exam;

import java.util.List;

import com.esprit.dto.classes.ClassDTO;

public class ExamDTO {

	private String examId;

	private String session;

	private List<ClassDTO> classes;

	public String getExamId() {
		return examId;
	}

	public ExamDTO examId(String examId) {
		this.examId = examId;
		return this;
	}

	public String getSession() {
		return session;
	}

	public ExamDTO session(String session) {
		this.session = session;
		return this;
	}

	public List<ClassDTO> getClasses() {
		return classes;
	}

	public ExamDTO classes(List<ClassDTO> classes) {
		this.classes = classes;
		return this;
	}

}