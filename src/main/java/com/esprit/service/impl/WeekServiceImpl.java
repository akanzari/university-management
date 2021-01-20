package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.esprit.domain.WeekEntity;
import com.esprit.dto.WeekDTO;
import com.esprit.repository.WeekRepository;
import com.esprit.service.WeekService;
import com.esprit.service.mapper.WeekMapper;

@Service
public class WeekServiceImpl implements WeekService {

	private final WeekRepository repository;

	private final WeekMapper mapper;

	public WeekServiceImpl(WeekRepository repository, WeekMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public void addWeek(WeekDTO weekDTO) {
		WeekEntity weekEntity = mapper.weekDTOToweekEntity(weekDTO);
		repository.save(weekEntity);
	}

	@Override
	public WeekEntity findWeek(String weekId) {
		WeekEntity result = null;
		if (!StringUtils.isBlank(weekId)) {
			Optional<WeekEntity> weekEntityOptional = repository.findById(weekId);
			if (weekEntityOptional.isPresent()) {
				result = weekEntityOptional.get();
			}
		}
		return result;
	}

	@Override
	public List<WeekDTO> findWeeks() {
		return mapper.weekEntitiesToweekDTO(repository.findAll());
	}

}