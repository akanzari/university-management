package com.esprit.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SEMESTER")
public class SemesterEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String semestreId;

	private String code;

	private Date startDate;

	private Date endDate;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_semester")
	private List<PeriodEntity> periods;

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

	public List<PeriodEntity> getPeriods() {
		return periods;
	}

	public void setPeriods(List<PeriodEntity> periods) {
		this.periods = periods;
	}

}
