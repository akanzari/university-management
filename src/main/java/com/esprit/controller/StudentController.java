package com.esprit.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.dto.user.CreateStudentRequest;
import com.esprit.dto.user.StudentResponse;
import com.esprit.service.StudentService;

@RestController
@RequestMapping("student")
public class StudentController {

	private final StudentService service;

	public StudentController(StudentService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> createStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest) {
		service.addStudent(createStudentRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("{userId}")
	public ResponseEntity<StudentResponse> findStudent(@PathVariable String userId) {
		return new ResponseEntity<>(service.findBydId(userId), HttpStatus.OK);
	}

}