package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.ClassEntity;
import com.esprit.domain.ModuleEntity;
import com.esprit.domain.TeacherEntity;
import com.esprit.dto.request.CreateModuleRequest;
import com.esprit.dto.request.UpdateModuleRequest;
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
	public ModuleResponse addModule(CreateModuleRequest createModuleRequest) {
		ModuleEntity moduleEntity = mapper.createModuleRequestToModuleEntity(createModuleRequest, teacherRepository,
				classRepository);

		if (!classRepository.findById(createModuleRequest.getClassId()).isPresent()) {
			throw new EntityNotFoundException(ClassEntity.class, "Id", createModuleRequest.getClassId());
		}

		if (!teacherRepository.findById(createModuleRequest.getTeacherId()).isPresent()) {
			throw new EntityNotFoundException(TeacherEntity.class, "Id", createModuleRequest.getTeacherId());
		}

		moduleEntity = moduleRepository.save(moduleEntity);
		return mapper.moduleEntityToModuleResponse(moduleEntity);
	}

	@Override
	public ModuleResponse updateModule(UpdateModuleRequest updateModuleRequest) {
		ModuleEntity moduleEntity;
		Optional<ModuleEntity> moduleEntityOptional = moduleRepository.findById(updateModuleRequest.getModuleId());
		if (moduleEntityOptional.isPresent()) {
			moduleEntity = mapper.updateModuleRequestToModuleEntity(updateModuleRequest, teacherRepository,
					classRepository);
			if (!classRepository.findById(updateModuleRequest.getClassId()).isPresent()) {
				throw new EntityNotFoundException(ClassEntity.class, "Id", updateModuleRequest.getClassId());
			}

			if (!teacherRepository.findById(updateModuleRequest.getTeacherId()).isPresent()) {
				throw new EntityNotFoundException(TeacherEntity.class, "Id", updateModuleRequest.getTeacherId());
			}
			moduleEntity.setCode(moduleEntityOptional.get().getCode());
			moduleEntity.setDesignation(moduleEntityOptional.get().getDesignation());
			moduleEntity = moduleRepository.save(moduleEntity);
		} else {
			throw new EntityNotFoundException(ModuleEntity.class, "Id", updateModuleRequest.getModuleId());
		}
		return mapper.moduleEntityToModuleResponse(moduleEntity);
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

}