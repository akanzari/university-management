package com.esprit.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StudentEntity extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String userId;

	@Column(unique = true)
	private String cin;

	@ManyToOne(optional = false)
	@JoinColumn(name = "class_id")
	private ClassEntity classs;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public ClassEntity getClasss() {
		return classs;
	}

	public void setClasss(ClassEntity classs) {
		this.classs = classs;
	}

}