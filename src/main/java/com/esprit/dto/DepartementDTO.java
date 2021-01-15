package com.esprit.dto;

public class DepartementDTO {

	private String code;

	private String label;

	public String getCode() {
		return code;
	}

	public DepartementDTO code(String code) {
		this.code = code;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public DepartementDTO label(String label) {
		this.label = label;
		return this;
	}

}