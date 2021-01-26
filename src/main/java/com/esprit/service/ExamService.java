package com.esprit.service;

import java.util.List;

import com.esprit.dto.exam.CreateExamRequest;
import com.esprit.dto.exam.DeleteExamDTO;
import com.esprit.dto.exam.ExamDTO;

public interface ExamService {

	void addExam(CreateExamRequest createExamRequest);

	void updateExam(CreateExamRequest updateExamRequest);

	void deleteExam(DeleteExamDTO deleteExamDTO);

	List<ExamDTO> findExams();

}