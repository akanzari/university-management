package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.DepartmentEntity;
import com.esprit.domain.SiteEntity;
import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.request.CreateDepartmentRequest;
import com.esprit.dto.response.DepartmentResponse;
import com.esprit.error.exception.EntityAlreadyExistsExeption;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.DepartmentRepository;
import com.esprit.repository.SiteRepository;
import com.esprit.repository.SpecialityRepository;
import com.esprit.service.DepartmentService;
import com.esprit.service.mapper.DepartmentMapper;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;

	private final SpecialityRepository specialityRepository;

	private final SiteRepository siteRepository;

	private final DepartmentMapper mapper;

	public DepartmentServiceImpl(DepartmentRepository departmentRepository, SpecialityRepository specialityRepository,
			SiteRepository siteRepository, DepartmentMapper mapper) {
		super();
		this.departmentRepository = departmentRepository;
		this.specialityRepository = specialityRepository;
		this.siteRepository = siteRepository;
		this.mapper = mapper;
	}

	@Override
	public DepartmentResponse addDepartement(CreateDepartmentRequest createDepartementRequest) {
		DepartmentEntity departmentEntity = mapper.createDepartmentRequestToDepartmentEntity(createDepartementRequest,
				siteRepository, specialityRepository);

		if (departmentRepository.findByCode(createDepartementRequest.getCode()) != null) {
			throw new EntityAlreadyExistsExeption(DepartmentEntity.class, "code", createDepartementRequest.getCode());
		}

		createDepartementRequest.getSpecialityIds().forEach(id -> {
			if (specialityRepository.getOne(id) == null) {
				throw new EntityAlreadyExistsExeption(SpecialityEntity.class, "id", id);
			}
		});

		createDepartementRequest.getSiteIds().forEach(id -> {
			if (siteRepository.getOne(id) == null) {
				throw new EntityAlreadyExistsExeption(SiteEntity.class, "id", id);
			}
		});

		departmentEntity = departmentRepository.save(departmentEntity);
		return mapper.departmentEntityToDepartmentResponse(departmentEntity);
	}

	@Override
	public void deleteDepartement(String departementId) {
		if (findDepartement(departementId) != null) {
			departmentRepository.deleteById(departementId);
		}
	}

	@Override
	public DepartmentResponse findDepartement(String departementId) {
		DepartmentResponse result = null;
		Optional<DepartmentEntity> departmentEntityOptional = departmentRepository.findById(departementId);
		if (departmentEntityOptional.isPresent()) {
			result = mapper.departmentEntityToDepartmentResponse(departmentEntityOptional.get());
		} else {
			throw new EntityNotFoundException(DepartmentEntity.class, "id", departementId);
		}
		return result;
	}

	@Override
	public List<DepartmentResponse> findDepartements() {
		return mapper.departmentEntitiesToDepartmentResponse(departmentRepository.findAll());
	}

}