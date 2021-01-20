package com.esprit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.esprit.domain.ClassEntity;
import com.esprit.dto.classes.ClassDTO;
import com.esprit.dto.classes.CreateClassRequest;
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
	public void addClass(CreateClassRequest createClassRequest) {
		ClassEntity classEntity = mapper.createClassRequestToClassEntity(createClassRequest);
		classRepository.save(classEntity);
	}

	@Override
	public void updateClass(CreateClassRequest createClassRequest) {
		ClassEntity classEntity;
		Optional<ClassEntity> classEntityOptional = classRepository.findById(createClassRequest.getClassId());
		if (classEntityOptional.isPresent()) {
			classEntity = mapper.createClassRequestToClassEntity(createClassRequest);
			classRepository.save(classEntity);
		} else {
			throw new EntityNotFoundException(ClassEntity.class, "Id", createClassRequest.getClassId());
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

	@Override
	public List<ClassDTO> searchClasses(String name, String email, String speciality, int nbrStudents) {
		return mapper.classEntitiesToClassDTO(classRepository.searchConventions(name, email, speciality, nbrStudents));
	}

	@Override
	public List<ClassEntity> findClassesByIds(List<String> classes) {
		List<ClassEntity> classEntities = new ArrayList<>();
		if (CollectionUtils.isEmpty(classes)) {
			classes.forEach(classId -> {
				Optional<ClassEntity> classEntityOptional = classRepository.findById(classId);
				if (classEntityOptional.isPresent()) {
					classEntities.add(classEntityOptional.get());
				}
			});
		}
		return classEntities;
	}

}