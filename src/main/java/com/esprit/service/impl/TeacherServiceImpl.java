package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.TeacherEntity;
import com.esprit.dto.TeacherDTO;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.TeacherRepository;
import com.esprit.service.IAMService;
import com.esprit.service.TeacherService;
import com.esprit.service.mapper.TeacherMapper;

@Service
public class TeacherServiceImpl implements TeacherService {

	private final TeacherRepository teacherRepository;

	private final IAMService iamService;

	private final TeacherMapper mapper;

	public TeacherServiceImpl(TeacherRepository teacherRepository, IAMService iamService, TeacherMapper mapper) {
		this.teacherRepository = teacherRepository;
		this.iamService = iamService;
		this.mapper = mapper;
	}

	@Override
	public void addTeacher(TeacherDTO teacherDTO) {
		TeacherEntity teacherEntity = mapper.teacherDTOtoTeacherEntity(teacherDTO);
		teacherEntity = teacherRepository.save(teacherEntity);
	}

	@Override
	public TeacherDTO findBydId(String userId) {
		TeacherDTO result = null;
		Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findById(userId);
		if (teacherEntityOptional.isPresent()) {
			result = mapper.teacherEntityToTeacherDTO(teacherEntityOptional.get(), iamService);
		} else {
			throw new EntityNotFoundException(TeacherDTO.class, "id", userId);
		}
		return result;
	}

	@Override
	public List<TeacherDTO> findTeachers() {
		return mapper.teacherEntitiesToTeacherDTO(teacherRepository.findAll(), iamService);
	}

}