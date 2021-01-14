package com.esprit.dto;

import java.util.Date;
import java.util.List;

public class TeacherDTO {

	private String teacherId;

	private int cin;

	private String sex;

	private String fullName;

	private String email;

	private List<String> phones;

	private String up;

	private String departement;

	private int nbrSurveillance;

	private int nbrHeureSurveillance;

	private Date startDate;

	private Date endDate;

	private String reason;

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public int getCin() {
		return cin;
	}

	public void setCin(int cin) {
		this.cin = cin;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public int getNbrSurveillance() {
		return nbrSurveillance;
	}

	public void setNbrSurveillance(int nbrSurveillance) {
		this.nbrSurveillance = nbrSurveillance;
	}

	public int getNbrHeureSurveillance() {
		return nbrHeureSurveillance;
	}

	public void setNbrHeureSurveillance(int nbrHeureSurveillance) {
		this.nbrHeureSurveillance = nbrHeureSurveillance;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}