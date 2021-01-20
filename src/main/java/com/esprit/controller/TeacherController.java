package com.esprit.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.dto.teacher.CreateTeacherRequest;
import com.esprit.dto.teacher.SearchTeacherDTO;
import com.esprit.dto.teacher.TeacherDTO;
import com.esprit.service.TeacherService;

@RestController
@RequestMapping("teacher")
public class TeacherController {

	private final TeacherService service;

	public TeacherController(TeacherService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> createTeacher(@RequestBody @Valid CreateTeacherRequest createTeacherRequest) {
		service.addTeacher(createTeacherRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("{userId}")
	public ResponseEntity<TeacherDTO> findTeacher(@PathVariable String userId) {
		return new ResponseEntity<>(service.findBydId(userId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<TeacherDTO>> findTeachers() {
		return new ResponseEntity<>(service.findTeachers(), HttpStatus.OK);
	}

	@PostMapping("ups")
	public ResponseEntity<List<TeacherDTO>> findAllByUps(@RequestBody @NotEmpty List<String> ups) {
		return new ResponseEntity<>(service.findAllByUps(ups), HttpStatus.OK);
	}

	@PostMapping("search")
	public ResponseEntity<List<TeacherDTO>> searcheachers(@RequestBody SearchTeacherDTO searchTeacherDTO) {
		return new ResponseEntity<>(service.searcheachers(searchTeacherDTO.getTeacherId(), searchTeacherDTO.getCin(),
				searchTeacherDTO.getDepartement(), searchTeacherDTO.getClasss()), HttpStatus.OK);
	}

}