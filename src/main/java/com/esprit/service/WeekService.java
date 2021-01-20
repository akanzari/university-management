package com.esprit.service;

import java.util.List;

import org.mapstruct.Named;

import com.esprit.domain.WeekEntity;
import com.esprit.dto.WeekDTO;

public interface WeekService {

	void addWeek(WeekDTO weekDTO);

	@Named("findWeek")
	WeekEntity findWeek(String weekId);

	List<WeekDTO> findWeeks();

}