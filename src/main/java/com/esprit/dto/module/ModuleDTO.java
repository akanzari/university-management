package com.esprit.dto.module;

import java.util.List;

public class ModuleDTO {

	private String moduleId;

	private String designation;

	private List<AssignClassModuleDTO> assignClasses;

	public String getModuleId() {
		return moduleId;
	}

	public ModuleDTO moduleId(String moduleId) {
		this.moduleId = moduleId;
		return this;
	}

	public String getDesignation() {
		return designation;
	}

	public ModuleDTO designation(String designation) {
		this.designation = designation;
		return this;
	}

	public List<AssignClassModuleDTO> getAssignClasses() {
		return assignClasses;
	}

	public ModuleDTO assignClasses(List<AssignClassModuleDTO> assignClasses) {
		this.assignClasses = assignClasses;
		return this;
	}

}