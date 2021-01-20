package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.esprit.domain.SemesterEntity;
import com.esprit.dto.SemesterDTO;
import com.esprit.repository.SemesterRepository;
import com.esprit.service.SemesterService;
import com.esprit.service.mapper.SemesterMapper;

@Service
public class SemesterServiceImpl implements SemesterService {

	private final SemesterRepository repository;

	private final SemesterMapper mapper;

	public SemesterServiceImpl(SemesterRepository repository, SemesterMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public void addSemester(SemesterDTO semesterDTO) {
		SemesterEntity semesterEntity = mapper.semesterDTOToSemesterEntity(semesterDTO);
		repository.save(semesterEntity);
	}

	@Override
	public List<SemesterDTO> findSemesters() {
		return mapper.semesterEntitiesToSemesterDTO(repository.findAll());
	}

	@Override
	public SemesterEntity findSemester(String semesterId) {
		SemesterEntity result = null;
		if (!StringUtils.isBlank(semesterId)) {
			Optional<SemesterEntity> semesterEntityOptional = repository.findById(semesterId);
			if (semesterEntityOptional.isPresent()) {
				result = semesterEntityOptional.get();
			}
		}
		return result;
	}

}