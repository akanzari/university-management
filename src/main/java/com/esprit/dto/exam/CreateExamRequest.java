package com.esprit.dto.exam;

import java.util.List;

public class CreateExamRequest {

	private List<Integer> levels;

	private String session;

	private List<CreateAssignClassExamRequest> assignClasses;

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

	public List<CreateAssignClassExamRequest> getAssignClasses() {
		return assignClasses;
	}

	public void setAssignClasses(List<CreateAssignClassExamRequest> assignClasses) {
		this.assignClasses = assignClasses;
	}

}