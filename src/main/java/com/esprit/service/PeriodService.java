package com.esprit.service;

import org.mapstruct.Named;

import com.esprit.domain.PeriodEntity;

public interface PeriodService {

	@Named("findPeriod")
	PeriodEntity findPeriod(String periodId);

}