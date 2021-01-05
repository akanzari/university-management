package com.esprit.dto.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class CreateDepartmentRequest {

	@NotBlank
	private String code;

	@NotBlank
	private String label;

	@NotEmpty
	private Set<String> specialityIds;

	@NotEmpty
	private Set<String> siteIds;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Set<String> getSpecialityIds() {
		return specialityIds;
	}

	public void setSpecialityIds(Set<String> specialityIds) {
		this.specialityIds = specialityIds;
	}

	public Set<String> getSiteIds() {
		return siteIds;
	}

	public void setSiteIds(Set<String> siteIds) {
		this.siteIds = siteIds;
	}

}