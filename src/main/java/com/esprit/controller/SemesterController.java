package com.esprit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.dto.SemesterDTO;
import com.esprit.service.SemesterService;

@RestController
@RequestMapping("semester")
public class SemesterController {

	private SemesterService service;

	public SemesterController(SemesterService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> createSemester(@RequestBody @Valid SemesterDTO semesterDTO) {
		service.addSemester(semesterDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<SemesterDTO>> findSemesters() {
		return new ResponseEntity<>(service.findSemesters(), HttpStatus.OK);
	}

}