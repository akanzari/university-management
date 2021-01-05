package com.esprit.dto.request;

import javax.validation.constraints.NotBlank;

public class CreateSpecialityRequest {

	@NotBlank
	private String code;

	@NotBlank
	private String label;

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

}