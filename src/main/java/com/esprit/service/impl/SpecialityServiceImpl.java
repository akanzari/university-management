package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.request.CreateSpecialityRequest;
import com.esprit.dto.response.SpecialityResponse;
import com.esprit.error.exception.EntityAlreadyExistsExeption;
import com.esprit.error.exception.EntityNotFoundException;
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
	public SpecialityResponse addSpeciality(CreateSpecialityRequest createSpecialityRequest) {
		SpecialityEntity specialityEntity = mapper.createSpecialityRequestToSpecialityEntity(createSpecialityRequest);

		if (repository.findByCode(createSpecialityRequest.getCode()) != null) {
			throw new EntityAlreadyExistsExeption(SpecialityEntity.class, "code", createSpecialityRequest.getCode());
		}

		specialityEntity = repository.save(specialityEntity);
		return mapper.specialityEntityToSpecialityResponse(specialityEntity);
	}

	@Override
	public void deleteSpeciality(String specialityId) {
		if (findSpecialitie(specialityId) != null) {
			repository.deleteById(specialityId);
		}
	}

	@Override
	public SpecialityResponse findSpecialitie(String specialityId) {
		SpecialityResponse result = null;
		Optional<SpecialityEntity> specialityEntityOptional = repository.findById(specialityId);
		if (specialityEntityOptional.isPresent()) {
			result = mapper.specialityEntityToSpecialityResponse(specialityEntityOptional.get());
		} else {
			throw new EntityNotFoundException(SpecialityEntity.class, "id", specialityId);
		}
		return result;
	}

	@Override
	public List<SpecialityResponse> findSpecialities() {
		return mapper.specialityEntitiesToSpecialityResponse(repository.findAll());
	}

}