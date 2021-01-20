package com.esprit.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.collections4.CollectionUtils;

@Entity
@Table(name = "ESP_MODULE")
public class ModuleEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String moduleId;

	private String designation;

	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AssignClassModuleEntity> assignClasses;

	public void addAssignClasses(List<AssignClassModuleEntity> assignClassModuleEntities) {
		if (CollectionUtils.isEmpty(assignClasses)) {
			assignClasses = new ArrayList<>();
		}
		assignClasses.addAll(assignClassModuleEntities);
		assignClassModuleEntities.forEach(item -> item.module(this));
	}
	
	public void removeAssignClasses(List<AssignClassModuleEntity> assignClassModuleEntities) {
		assignClasses.removeAll(assignClassModuleEntities);
		assignClassModuleEntities.forEach(item -> item.module(null));
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

	public List<AssignClassModuleEntity> getAssignClasses() {
		return assignClasses;
	}

	public void setAssignClasses(List<AssignClassModuleEntity> assignClasses) {
		this.assignClasses = assignClasses;
	}

}