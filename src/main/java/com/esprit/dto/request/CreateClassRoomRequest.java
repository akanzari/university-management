package com.esprit.dto.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateClassRoomRequest {

	@NotBlank
	private String code;

	@NotBlank
	private String label;

	@NotNull
	private int capacity;

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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Set<String> getSiteIds() {
		return siteIds;
	}

	public void setSiteIds(Set<String> siteIds) {
		this.siteIds = siteIds;
	}

}