package com.esprit.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TeacherEntity extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String userId;

	private String phone;

	private int nbrSurveillance;

	private int nbrHeureSurveillance;

	private Date startDate;

	private Date endDate;

	private String reason;

	@ManyToOne(optional = false)
	@JoinColumn(name = "department_id")
	private DepartmentEntity departmentEntity;

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

	public DepartmentEntity getDepartmentEntity() {
		return departmentEntity;
	}

	public void setDepartmentEntity(DepartmentEntity departmentEntity) {
		this.departmentEntity = departmentEntity;
	}

}