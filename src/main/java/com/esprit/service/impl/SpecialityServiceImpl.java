package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.SpecialityDTO;
import com.esprit.repository.SpecialityRepository;
import com.esprit.service.SpecialityService;
import com.esprit.service.mapper.SpecialityMapper;

@Service
public class SpecialityServiceImpl implements SpecialityService {

	private final SpecialityRepository repository;

	private final SpecialityMapper mapper;

	public SpecialityServiceImpl(SpecialityRepository repository, SpecialityMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public void addSpeciality(SpecialityDTO specialityDTO) {
		SpecialityEntity specialityEntity = mapper.specialityDTOToSpecialityEntity(specialityDTO);
		repository.save(specialityEntity);
	}

	@Override
	public SpecialityEntity findSpeciality(String specialityId) {
		SpecialityEntity result = null;
		if (!StringUtils.isBlank(specialityId)) {
			Optional<SpecialityEntity> specialityEntityOptional = repository.findById(specialityId);
			if (specialityEntityOptional.isPresent()) {
				result = specialityEntityOptional.get();
			}
		}
		return result;
	}

	@Override
	public List<SpecialityDTO> findSpecialities() {
		return mapper.specialityEntitiesToSpecialityDTO(repository.findAll());
	}

}