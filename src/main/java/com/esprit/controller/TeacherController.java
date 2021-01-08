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

import com.esprit.dto.request.CreateTeacherRequest;
import com.esprit.dto.response.TeacherResponse;
import com.esprit.service.TeacherService;

@RestController
@RequestMapping("teacher")
public class TeacherController {

	private final TeacherService service;

	public TeacherController(TeacherService service) {
		this.service = service;
	};

	@PostMapping
	public ResponseEntity<Void> createTeacher(@RequestBody @Valid CreateTeacherRequest createTeacherRequest) {
		service.addTeacher(createTeacherRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("{userId}")
	public ResponseEntity<TeacherResponse> findTeacher(@PathVariable String userId) {
		return new ResponseEntity<>(service.findBydId(userId), HttpStatus.OK);
	}

}