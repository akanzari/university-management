package com.esprit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.dto.exam.CreateExamRequest;
import com.esprit.dto.exam.ExamDTO;
import com.esprit.dto.exam.UpdateExamRequest;
import com.esprit.service.ExamService;

@RestController
@RequestMapping("exam")
public class ExamController {

	private final ExamService service;

	public ExamController(ExamService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> createExam(@RequestBody @Valid CreateExamRequest createExamRequest) {
		service.addExam(createExamRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Void> upateExam(@RequestBody @Valid UpdateExamRequest updateExamRequest) {
		service.updateExam(updateExamRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("{examId}")
	public ResponseEntity<Void> deleteExam(@PathVariable String examId) {
		service.deleteExam(examId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<ExamDTO>> findExams() {
		return new ResponseEntity<>(service.findExams(), HttpStatus.OK);
	}

}