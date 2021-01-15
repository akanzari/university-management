package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.ModuleEntity;
import com.esprit.dto.request.modules.CreateModuleRequest;
import com.esprit.dto.response.ModuleResponse;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.ModuleRepository;
import com.esprit.repository.TeacherRepository;
import com.esprit.service.ModuleService;
import com.esprit.service.mapper.ModuleMapper;

@Service
public class ModuleServiceImpl implements ModuleService {

	private final ModuleRepository moduleRepository;

	private final TeacherRepository teacherRepository;

	private final ClassRepository classRepository;

	private final ModuleMapper mapper;

	public ModuleServiceImpl(ModuleRepository moduleRepository, TeacherRepository teacherRepository,
			ClassRepository classRepository, ModuleMapper mapper) {
		this.moduleRepository = moduleRepository;
		this.teacherRepository = teacherRepository;
		this.classRepository = classRepository;
		this.mapper = mapper;
	}

	@Override
	public void addModule(CreateModuleRequest createModuleRequest) {
		ModuleEntity moduleEntity = mapper.createModuleRequestToModuleEntity(createModuleRequest, teacherRepository, classRepository);
		moduleRepository.save(moduleEntity);
	}

	@Override
	public void deleteModule(String moduleId) {
		if (findModule(moduleId) != null) {
			moduleRepository.deleteById(moduleId);
		}
	}

	@Override
	public ModuleResponse findModule(String moduleId) {
		ModuleResponse result = null;
		Optional<ModuleEntity> moduleEntityOptional = moduleRepository.findById(moduleId);
		if (moduleEntityOptional.isPresent()) {
			result = mapper.moduleEntityToModuleResponse(moduleEntityOptional.get());
		} else {
			throw new EntityNotFoundException(ModuleEntity.class, "id", moduleId);
		}
		return result;
	}

	@Override
	public List<ModuleResponse> findModules() {
		return mapper.moduleEntitiesToModuleResponse(moduleRepository.findAll());
	}

	@Override
	public List<ModuleResponse> findModulesByClass(String classId) {
		return null;
	}

}