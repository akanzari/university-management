package com.esprit.dto.module;

import java.util.List;

public class CreateModuleRequest {

	private String moduleId;

	private String designation;

	private String up;

	private List<AssignClassToModuleRequest> assignClasses;

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public List<AssignClassToModuleRequest> getAssignClasses() {
		return assignClasses;
	}

	public void setAssignClasses(List<AssignClassToModuleRequest> assignClasses) {
		this.assignClasses = assignClasses;
	}

}