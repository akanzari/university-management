package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.esprit.domain.SeanceEntity;
import com.esprit.dto.seance.CreateSeanceRequest;
import com.esprit.dto.seance.SeanceDTO;
import com.esprit.repository.SeanceRepository;
import com.esprit.service.SeanceService;
import com.esprit.service.mapper.SeanceMapper;

@Service
public class SeanceServiceImpl implements SeanceService {

	private final SeanceRepository repository;

	private final SeanceMapper mapper;

	public SeanceServiceImpl(SeanceRepository repository, SeanceMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public void addSeance(CreateSeanceRequest createSeanceRequest) {
		SeanceEntity seanceEntity = mapper.createSeanceRequestToSeanceEntity(createSeanceRequest);
		repository.save(seanceEntity);
	}

	@Override
	public SeanceEntity findSeance(String seanceId) {
		SeanceEntity result = null;
		if (!StringUtils.isBlank(seanceId)) {
			Optional<SeanceEntity> seanceEntityOptional = repository.findById(seanceId);
			if (seanceEntityOptional.isPresent()) {
				result = seanceEntityOptional.get();
			}
		}
		return result;
	}

	@Override
	public List<SeanceDTO> findSeances() {
		return mapper.SeanceEntitiesToseanceDTO(repository.findAll());
	}

}