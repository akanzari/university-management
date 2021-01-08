package com.esprit.dto.response;

import java.util.Date;

public class TeacherResponse {

	private String userId;

	private String phone;

	private int nbrSurveillance;

	private int nbrHeureSurveillance;

	private Date startDate;

	private Date endDate;

	private String reason;

	private DepartmentResponse department;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public DepartmentResponse getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentResponse department) {
		this.department = department;
	}

}