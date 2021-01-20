package com.esprit.dto.module;

public class ModuleWithoutAssignClassesDTO {
	
	private String moduleId;

	private String designation;

	public ModuleWithoutAssignClassesDTO(String moduleId, String designation) {
		this.moduleId = moduleId;
		this.designation = designation;
	}

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

}