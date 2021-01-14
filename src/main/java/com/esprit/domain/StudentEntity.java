package com.esprit.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StudentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String userId;

	private String fullName;

	private int cin;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_class")
	private ClassEntity classs;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getCin() {
		return cin;
	}

	public void setCin(int cin) {
		this.cin = cin;
	}

	public ClassEntity getClasss() {
		return classs;
	}

	public void setClasss(ClassEntity classs) {
		this.classs = classs;
	}

}