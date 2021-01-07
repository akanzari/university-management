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

import com.esprit.dto.request.CreateClassRequest;
import com.esprit.dto.request.UpdateClassRequest;
import com.esprit.dto.response.ClassResponse;
import com.esprit.service.ClassService;

@RestController
@RequestMapping("class")
public class ClassController {

	private final ClassService service;

	public ClassController(ClassService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ClassResponse> createClass(@RequestBody @Valid CreateClassRequest createClassRequest) {
		return new ResponseEntity<>(service.addClass(createClassRequest), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<ClassResponse> createClass(@RequestBody @Valid UpdateClassRequest updateClassRequest) {
		return new ResponseEntity<>(service.updateClass(updateClassRequest), HttpStatus.CREATED);
	}

	@DeleteMapping("{classId}")
	public ResponseEntity<Void> deleteClass(@PathVariable String classId) {
		service.deleteClass(classId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("{classId}")
	public ResponseEntity<ClassResponse> findClass(@PathVariable String classId) {
		return new ResponseEntity<>(service.findClasse(classId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<ClassResponse>> findClasses() {
		return new ResponseEntity<>(service.findClasses(), HttpStatus.OK);
	}

}