package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.esprit.domain.DepartmentEntity;
import com.esprit.dto.DepartmentDTO;
import com.esprit.repository.DepartementReposiroty;
import com.esprit.service.DepartmentService;
import com.esprit.service.mapper.DepartmenetMapper;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartementReposiroty repository;

	private final DepartmenetMapper mapper;

	public DepartmentServiceImpl(DepartementReposiroty repository, DepartmenetMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public void addDepartment(DepartmentDTO departmentDTO) {
		DepartmentEntity departmentEntity = mapper.departmentDTOToDepartmentEntity(departmentDTO);
		repository.save(departmentEntity);
	}

	@Override
	public DepartmentEntity findDepartment(String departmentId) {
		DepartmentEntity result = null;
		if (!StringUtils.isBlank(departmentId)) {
			Optional<DepartmentEntity> departmentEntityOptional = repository.findById(departmentId);
			if (departmentEntityOptional.isPresent()) {
				result = departmentEntityOptional.get();
			}
		}
		return result;
	}

	@Override
	public List<DepartmentDTO> findDepartments() {
		return mapper.departmentEntitiesToDepartementDTO(repository.findAll());
	}

}