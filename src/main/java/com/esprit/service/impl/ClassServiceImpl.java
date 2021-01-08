package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.ClassEntity;
import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.request.CreateClassRequest;
import com.esprit.dto.request.UpdateClassRequest;
import com.esprit.dto.response.ClassResponse;
import com.esprit.error.exception.EntityAlreadyExistsExeption;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.SpecialityRepository;
import com.esprit.service.ClassService;
import com.esprit.service.mapper.ClassMapper;

@Service
public class ClassServiceImpl implements ClassService {

	private final ClassRepository classRepository;

	private final SpecialityRepository specialityRepository;

	private final ClassMapper mapper;

	public ClassServiceImpl(ClassRepository classRepository, SpecialityRepository specialityRepository,
			ClassMapper mapper) {
		this.classRepository = classRepository;
		this.specialityRepository = specialityRepository;
		this.mapper = mapper;
	}

	@Override
	public ClassResponse addClass(CreateClassRequest createClassRequest) {
		ClassEntity classEntity = mapper.createClassRequestRequestToClassEntity(createClassRequest,
				specialityRepository);

		if (classRepository.findByCode(createClassRequest.getCode()) != null) {
			throw new EntityAlreadyExistsExeption(ClassEntity.class, "code", createClassRequest.getCode());
		}

		if (!specialityRepository.findById(createClassRequest.getSpecialityId()).isPresent()) {
			throw new EntityNotFoundException(SpecialityEntity.class, "Id", createClassRequest.getSpecialityId());
		}

		classEntity = classRepository.save(classEntity);
		return mapper.classEntityToClassResponse(classEntity);
	}

	@Override
	public ClassResponse updateClass(UpdateClassRequest updateClassRequest) {
		ClassEntity classEntity;
		Optional<ClassEntity> classEntityOptional = classRepository.findById(updateClassRequest.getClassId());
		if (classEntityOptional.isPresent()) {
			classEntity = mapper.updateClassRequestRequestToClassEntity(updateClassRequest, specialityRepository);
			if (specialityRepository.getOne(updateClassRequest.getSpecialityId()) == null) {
				throw new EntityNotFoundException(SpecialityEntity.class, "Id", updateClassRequest.getSpecialityId());
			}
			classEntity.setCode(classEntityOptional.get().getCode());
			classEntity = classRepository.save(classEntity);
		} else {
			throw new EntityNotFoundException(ClassEntity.class, "Id", updateClassRequest.getClassId());
		}
		return mapper.classEntityToClassResponse(classEntity);
	}

	@Override
	public void deleteClass(String classId) {
		if (findClasse(classId) != null) {
			classRepository.deleteById(classId);
		}
	}

	@Override
	public ClassResponse findClasse(String classId) {
		ClassResponse result = null;
		Optional<ClassEntity> classEntityOptional = classRepository.findById(classId);
		if (classEntityOptional.isPresent()) {
			result = mapper.classEntityToClassResponse(classEntityOptional.get());
		} else {
			throw new EntityNotFoundException(ClassEntity.class, "id", classId);
		}
		return result;
	}

	@Override
	public List<ClassResponse> findClasses() {
		return mapper.classEntitiesToClassResponse(classRepository.findAll());
	}

}