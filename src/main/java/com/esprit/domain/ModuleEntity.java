package com.esprit.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class ModuleEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String moduleId;

	private String designation;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_module")
	private List<AssignClassEntity> assignClasses;

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

	public List<AssignClassEntity> getAssignClasses() {
		return assignClasses;
	}

	public void setAssignClasses(List<AssignClassEntity> assignClasses) {
		this.assignClasses = assignClasses;
	}

}