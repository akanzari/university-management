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

import com.esprit.dto.ClassDTO;
import com.esprit.service.ClassService;

@RestController
@RequestMapping("class")
public class ClassController {

	private final ClassService service;

	public ClassController(ClassService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> createClass(@RequestBody @Valid ClassDTO classDTO) {
		service.addClass(classDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Void> updateClass(@RequestBody @Valid ClassDTO classDTO) {
		service.updateClass(classDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("{classId}")
	public ResponseEntity<Void> deleteClass(@PathVariable String classId) {
		service.deleteClass(classId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("{classId}")
	public ResponseEntity<ClassDTO> findClass(@PathVariable String classId) {
		return new ResponseEntity<>(service.findClass(classId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<ClassDTO>> findClasses() {
		return new ResponseEntity<>(service.findClasses(), HttpStatus.OK);
	}

	@GetMapping("moduleId/{moduleId}")
	public ResponseEntity<List<ClassDTO>> findClassesByModule(@PathVariable String moduleId) {
		return new ResponseEntity<>(service.findClassesByModule(moduleId), HttpStatus.OK);
	}

}