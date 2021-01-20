package com.esprit.service;

import java.util.List;

import com.esprit.dto.exam.CreateExamRequest;
import com.esprit.dto.exam.ExamDTO;
import com.esprit.dto.exam.UpdateExamRequest;

public interface ExamService {

	void addExam(CreateExamRequest createExamRequest);

	void updateExam(UpdateExamRequest updateExamRequest);

	void deleteExam(String examId);

	List<ExamDTO> findExams();

}