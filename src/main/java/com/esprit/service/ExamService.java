package com.esprit.service;

import java.util.List;

import com.esprit.dto.request.exams.CreateExamRequest;
import com.esprit.dto.request.exams.UpdateExamRequest;
import com.esprit.dto.response.ExamResponse;

public interface ExamService {

	void addExam(CreateExamRequest createExamRequest);

	void updateExam(UpdateExamRequest updateExamRequest);

	void deleteExam(String examId);

	ExamResponse findExam(String examId);

	List<ExamResponse> findExams();

}