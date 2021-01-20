package com.esprit.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.ClassEntity;
import com.esprit.domain.StudentEntity;
import com.esprit.dto.user.CreateStudentRequest;
import com.esprit.dto.user.StudentResponse;
import com.esprit.error.exception.EntityAlreadyExistsExeption;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.StudentRepository;
import com.esprit.service.StudentService;
import com.esprit.service.mapper.UserMapper;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

	private final ClassRepository classRepository;

	private final UserMapper mapper;

	public StudentServiceImpl(StudentRepository studentRepository, ClassRepository classRepository, UserMapper mapper) {
		this.studentRepository = studentRepository;
		this.classRepository = classRepository;
		this.mapper = mapper;
	}

	@Override
	public void addStudent(CreateStudentRequest createStudentRequest) {
		StudentEntity studentEntity = mapper.createStudentRequestToStudentEntity(createStudentRequest, classRepository);

		if (studentRepository.findById(createStudentRequest.getUserId()).isPresent()
				&& studentRepository.findByCin(createStudentRequest.getCin()) != null) {
			throw new EntityAlreadyExistsExeption(StudentEntity.class, "cin",
					Integer.toString(createStudentRequest.getCin()));
		}

		if (!classRepository.findById(createStudentRequest.getClassId()).isPresent()) {
			throw new EntityNotFoundException(ClassEntity.class, "id", createStudentRequest.getClassId());
		}

		studentRepository.save(studentEntity);
	}

	@Override
	public StudentResponse findBydId(String userId) {
		StudentResponse result = null;
		Optional<StudentEntity> studentEntityOptional = studentRepository.findById(userId);
		if (studentEntityOptional.isPresent()) {
			result = mapper.studentEntityToStudentResponse(studentEntityOptional.get());
		} else {
			throw new EntityNotFoundException(StudentEntity.class, "id", userId);
		}
		return result;
	}

}