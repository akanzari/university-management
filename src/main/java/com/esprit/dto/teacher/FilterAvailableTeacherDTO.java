package com.esprit.dto.teacher;

import java.util.Date;
import java.util.List;

public class FilterAvailableTeacherDTO {

	private Date effectDate;

	private int hour;

	private List<String> ups;

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public List<String> getUps() {
		return ups;
	}

	public void setUps(List<String> ups) {
		this.ups = ups;
	}

}