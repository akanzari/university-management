package com.esprit.dto.module;

import java.util.List;

public class ModuleDTO {

	private String moduleId;

	private String designation;

	private List<AssignClassModuleDTO> assignClasses;

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

	public List<AssignClassModuleDTO> getAssignClasses() {
		return assignClasses;
	}

	public void setAssignClasses(List<AssignClassModuleDTO> assignClasses) {
		this.assignClasses = assignClasses;
	}

}