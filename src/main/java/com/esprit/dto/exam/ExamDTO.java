package com.esprit.dto.exam;

import java.util.List;

public class ExamDTO {

	private String examId;

	private List<Integer> levels;

	private String session;

	private List<AssignClassExamDTO> assignClasses;

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public List<Integer> getLevels() {
		return levels;
	}

	public void setLevels(List<Integer> levels) {
		this.levels = levels;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public List<AssignClassExamDTO> getAssignClasses() {
		return assignClasses;
	}

	public void setAssignClasses(List<AssignClassExamDTO> assignClasses) {
		this.assignClasses = assignClasses;
	}

}