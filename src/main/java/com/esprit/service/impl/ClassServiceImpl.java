package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.ClassEntity;
import com.esprit.dto.ClassDTO;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.ClassRepository;
import com.esprit.service.ClassService;
import com.esprit.service.mapper.ClassMapper;

@Service
public class ClassServiceImpl implements ClassService {

	private final ClassRepository classRepository;

	private final ClassMapper mapper;

	public ClassServiceImpl(ClassRepository classRepository, ClassMapper mapper) {
		this.classRepository = classRepository;
		this.mapper = mapper;
	}

	@Override
	public void addClass(ClassDTO classDTO) {
		ClassEntity classEntity = mapper.classDTOToClassEntity(classDTO);
		classRepository.save(classEntity);
	}

	@Override
	public void updateClass(ClassDTO classDTO) {
		ClassEntity classEntity;
		Optional<ClassEntity> classEntityOptional = classRepository.findById(classDTO.getClassId());
		if (classEntityOptional.isPresent()) {
			classEntity = mapper.classDTOToClassEntity(classDTO);
			classRepository.save(classEntity);
		} else {
			throw new EntityNotFoundException(ClassEntity.class, "Id", classDTO.getClassId());
		}
	}

	@Override
	public void deleteClass(String classId) {
		if (findClass(classId) != null) {
			classRepository.deleteById(classId);
		}
	}

	@Override
	public ClassDTO findClass(String classId) {
		ClassDTO result = null;
		Optional<ClassEntity> classEntityOptional = classRepository.findById(classId);
		if (classEntityOptional.isPresent()) {
			result = mapper.classEntityToClassDTO(classEntityOptional.get());
		} else {
			throw new EntityNotFoundException(ClassEntity.class, "id", classId);
		}
		return result;
	}

	@Override
	public List<ClassDTO> findClasses() {
		return mapper.classEntitiesToClassDTO(classRepository.findAll());
	}

	@Override
	public List<ClassDTO> findClassesByModule(String moduleId) {
		return null;
	}

}