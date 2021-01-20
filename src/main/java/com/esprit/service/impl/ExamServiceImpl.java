package com.esprit.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.esprit.domain.ExamEntity;
import com.esprit.dto.exam.CreateExamRequest;
import com.esprit.dto.exam.ExamDTO;
import com.esprit.dto.exam.UpdateExamRequest;
import com.esprit.repository.ExamRepository;
import com.esprit.service.ExamService;
import com.esprit.service.TeacherService;
import com.esprit.service.mapper.ExamMapper;
import com.esprit.service.mapper.ExamResponseMapper;

@Service
public class ExamServiceImpl implements ExamService {

	private final ExamRepository examRepository;

	private final TeacherService teacherService;

	private final ExamMapper mapper;

	private final ExamResponseMapper responseMapper;

	public ExamServiceImpl(ExamRepository examRepository, TeacherService teacherService, ExamMapper mapper,
			ExamResponseMapper responseMapper) {
		this.examRepository = examRepository;
		this.teacherService = teacherService;
		this.mapper = mapper;
		this.responseMapper = responseMapper;
	}

	@Override
	public void addExam(CreateExamRequest createExamRequest) {
		ExamEntity examEntity = mapper.createExamRequestToExamEntity(createExamRequest);
		if (CollectionUtils.isNotEmpty(examEntity.getAssignClasses())) {
			examEntity.addAssignClasses(examEntity.getAssignClasses());
		}

		createExamRequest.getAssignClasses().forEach(item -> {
			item.getTeachers().forEach(teacher -> {
				teacherService.addDisponibilityToTeacher(teacher, item.getExamDate(), item.getExamHour(),
						item.getExamDuration());
			});
		});

		examRepository.save(examEntity);
	}

	@Override
	public void updateExam(UpdateExamRequest updateExamRequest) {
		/*
		 * ExamEntity examEntity; Optional<ExamEntity> examEntityOptional =
		 * examRepository.findById(updateExamRequest.getExamId()); if
		 * (examEntityOptional.isPresent()) { examEntity =
		 * mapper.updateExamRequestToExamEntity(updateExamRequest);
		 * examRepository.save(examEntity); } else { throw new
		 * EntityNotFoundException(ExamEntity.class, "Id",
		 * updateExamRequest.getExamId()); }
		 */
	}

	@Override
	public void deleteExam(String examId) {
		/*
		 * if (findExam(examId) != null) { examRepository.deleteById(examId); }
		 */
	}

	@Override
	public List<ExamDTO> findExams() {
		return responseMapper.examEntitiesToExamDTO(examRepository.findAll());
	}

}