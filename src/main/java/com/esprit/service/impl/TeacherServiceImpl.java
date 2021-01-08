package com.esprit.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.TeacherEntity;
import com.esprit.dto.request.CreateTeacherRequest;
import com.esprit.dto.response.TeacherResponse;
import com.esprit.error.exception.EntityAlreadyExistsExeption;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.DepartmentRepository;
import com.esprit.repository.TeacherRepository;
import com.esprit.service.TeacherService;
import com.esprit.service.mapper.UserMapper;

@Service
public class TeacherServiceImpl implements TeacherService {

	private final TeacherRepository teacherRepository;

	private final DepartmentRepository departmentRepository;

	private final UserMapper mapper;

	public TeacherServiceImpl(TeacherRepository teacherRepository, DepartmentRepository departmentRepository,
			UserMapper mapper) {
		this.teacherRepository = teacherRepository;
		this.departmentRepository = departmentRepository;
		this.mapper = mapper;
	}

	@Override
	public void addTeacher(CreateTeacherRequest createTeacherRequest) {
		TeacherEntity teacherEntity = mapper.createTeacherRequestoTeacherEntity(createTeacherRequest,
				departmentRepository);

		if (teacherRepository.findById(createTeacherRequest.getUserId()).isPresent()) {
			throw new EntityAlreadyExistsExeption(TeacherEntity.class, "id", createTeacherRequest.getUserId());
		}

		if (!departmentRepository.findById(createTeacherRequest.getDepartmentId()).isPresent()) {
			throw new EntityNotFoundException(TeacherEntity.class, "id", createTeacherRequest.getDepartmentId());
		}

		teacherRepository.save(teacherEntity);
	}

	@Override
	public TeacherResponse findBydId(String userId) {
		TeacherResponse result = null;
		Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findById(userId);
		if (teacherEntityOptional.isPresent()) {
			result = mapper.teacherEntityToTeacherResponse(teacherEntityOptional.get());
		} else {
			throw new EntityNotFoundException(TeacherResponse.class, "id", userId);
		}
		return result;
	}

}