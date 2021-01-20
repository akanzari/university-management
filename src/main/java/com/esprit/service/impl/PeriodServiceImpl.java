package com.esprit.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.esprit.domain.PeriodEntity;
import com.esprit.repository.PeriodRepository;
import com.esprit.service.PeriodService;

@Service
public class PeriodServiceImpl implements PeriodService {

	private final PeriodRepository repository;

	public PeriodServiceImpl(PeriodRepository repository) {
		this.repository = repository;
	}

	@Override
	public PeriodEntity findPeriod(String periodId) {
		PeriodEntity result = null;
		if (!StringUtils.isBlank(periodId)) {
			Optional<PeriodEntity> periodEntityOptional = repository.findById(periodId);
			if (periodEntityOptional.isPresent()) {
				result = periodEntityOptional.get();
			}
		}
		return result;
	}

}