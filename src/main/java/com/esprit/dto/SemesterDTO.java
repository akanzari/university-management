package com.esprit.dto;

import java.util.Date;
import java.util.List;

public class SemesterDTO {

	private String semestreId;

	private String code;

	private Date startDate;

	private Date endDate;

	private List<PeriodDTO> periods;

	public String getSemestreId() {
		return semestreId;
	}

	public void setSemestreId(String semestreId) {
		this.semestreId = semestreId;
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

	public List<PeriodDTO> getPeriods() {
		return periods;
	}

	public void setPeriods(List<PeriodDTO> periods) {
		this.periods = periods;
	}

}