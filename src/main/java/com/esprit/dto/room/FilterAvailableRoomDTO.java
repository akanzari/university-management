package com.esprit.dto.room;

import java.util.Date;
import java.util.List;

public class FilterAvailableRoomDTO {

	private Date effectDate;

	private int hour;

	private List<String> blocs;

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

	public List<String> getBlocs() {
		return blocs;
	}

	public void setBlocs(List<String> blocs) {
		this.blocs = blocs;
	}

}