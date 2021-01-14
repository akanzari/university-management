package com.esprit.dto.request.rooms;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateClassRoomRequest {

	@NotBlank
	private String classRoomId;

	@NotBlank
	private String label;

	@NotNull
	private int capacity;

	private Date startDate;

	private Date endDate;

	private int startHour;

	private int endHour;

	private String reasonId;

	@NotBlank
	private String blocId;

	@NotBlank
	private String siteId;

	public String getClassRoomId() {
		return classRoomId;
	}

	public void setClassRoomId(String classRoomId) {
		this.classRoomId = classRoomId;
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

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getBlocId() {
		return blocId;
	}

	public void setBlocId(String blocId) {
		this.blocId = blocId;
	}

}