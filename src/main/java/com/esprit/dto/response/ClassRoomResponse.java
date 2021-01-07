package com.esprit.dto.response;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ClassRoomResponse {

	private String classRoomId;

	private String code;

	private String label;

	private int capacity;

	private List<SiteResponse> sites;

	private Date startDate;

	private Date endDate;

	private String startHour;

	private String endHour;

	private String reason;

	private DepartmentResponse department;

	protected LocalDateTime createdDate;

	public String getClassRoomId() {
		return classRoomId;
	}

	public void setClassRoomId(String classRoomId) {
		this.classRoomId = classRoomId;
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public List<SiteResponse> getSites() {
		return sites;
	}

	public void setSites(List<SiteResponse> sites) {
		this.sites = sites;
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

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
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

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

}