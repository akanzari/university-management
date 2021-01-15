package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.ExamEntity;
import com.esprit.dto.request.exams.CreateExamRequest;
import com.esprit.dto.request.exams.UpdateExamRequest;
import com.esprit.dto.response.ExamResponse;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.RoomRepository;
import com.esprit.repository.ExamRepository;
import com.esprit.repository.ModuleRepository;
import com.esprit.repository.TeacherRepository;
import com.esprit.service.ExamService;
import com.esprit.service.mapper.ExamMapper;

@Service
public class ExamServiceImpl implements ExamService {

	private final ExamRepository examRepository;

	private final ClassRepository classRepository;

	private final ModuleRepository moduleRepository;

	private final RoomRepository classRoomRepository;

	private final TeacherRepository teacherRepository;

	private final ExamMapper mapper;

	public ExamServiceImpl(ExamRepository examRepository, ClassRepository classRepository,
			ModuleRepository moduleRepository, RoomRepository classRoomRepository,
			TeacherRepository teacherRepository, ExamMapper mapper) {
		this.examRepository = examRepository;
		this.classRepository = classRepository;
		this.moduleRepository = moduleRepository;
		this.classRoomRepository = classRoomRepository;
		this.teacherRepository = teacherRepository;
		this.mapper = mapper;
	}

	@Override
	public void addExam(CreateExamRequest createExamRequest) {
		ExamEntity examEntity = mapper.createExamRequestToExamEntity(createExamRequest, classRepository,
				moduleRepository, classRoomRepository, teacherRepository);
		examRepository.save(examEntity);
	}

	@Override
	public void updateExam(UpdateExamRequest updateExamRequest) {
		ExamEntity examEntity;
		Optional<ExamEntity> examEntityOptional = examRepository.findById(updateExamRequest.getExamId());
		if (examEntityOptional.isPresent()) {
			examEntity = mapper.updateExamRequestToExamEntity(updateExamRequest, classRepository, moduleRepository,
					classRoomRepository, teacherRepository);
			examRepository.save(examEntity);
		} else {
			throw new EntityNotFoundException(ExamEntity.class, "Id", updateExamRequest.getExamId());
		}
	}

	@Override
	public void deleteExam(String examId) {
		if (findExam(examId) != null) {
			examRepository.deleteById(examId);
		}
	}

	@Override
	public ExamResponse findExam(String examId) {
		ExamResponse result = null;
		Optional<ExamEntity> examEntityOptional = examRepository.findById(examId);
		if (examEntityOptional.isPresent()) {
			result = mapper.examEntityToExamResponse(examEntityOptional.get());
		} else {
			throw new EntityNotFoundException(ExamEntity.class, "id", examId);
		}
		return result;
	}

	@Override
	public List<ExamResponse> findExams() {
		return mapper.examEntitiessToExamResponse(examRepository.findAll());
	}

}