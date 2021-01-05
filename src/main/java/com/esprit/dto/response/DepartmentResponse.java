package com.esprit.dto.response;

import java.util.List;

public class DepartmentResponse {

	private String departmentId;

	private String code;

	private String label;

	private List<SpecialityResponse> specialities;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

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

	public List<SpecialityResponse> getSpecialities() {
		return specialities;
	}

	public void setSpecialities(List<SpecialityResponse> specialities) {
		this.specialities = specialities;
	}

}