package com.esprit.dto;

import java.util.Date;
import java.util.List;

public class PeriodDTO {

	private String periodId;

	private String code;

	private Date startDate;

	private Date endDate;

	private List<WeekDTO> weeks;

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public List<WeekDTO> getWeeks() {
		return weeks;
	}

	public void setWeeks(List<WeekDTO> weeks) {
		this.weeks = weeks;
	}

}