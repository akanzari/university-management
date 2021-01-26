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

import com.esprit.dto.classes.ClassDTO;
import com.esprit.dto.classes.CreateClassRequest;
import com.esprit.dto.classes.SearchClassDTO;
import com.esprit.service.ClassService;

@RestController
@RequestMapping("class")
public class ClassController {

	private final ClassService service;

	public ClassController(ClassService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> createClass(@RequestBody @Valid CreateClassRequest createClassRequest) {
		service.addClass(createClassRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Void> updateClass(@RequestBody @Valid CreateClassRequest createClassRequest) {
		service.updateClass(createClassRequest);
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

	@PostMapping("search")
	public ResponseEntity<List<ClassDTO>> searchClasses(@RequestBody SearchClassDTO searchClassDTO) {
		return new ResponseEntity<>(service.searchClasses(searchClassDTO.getClassId(), searchClassDTO.getEmail(),
				searchClassDTO.getSpeciality(), searchClassDTO.getNbrStudents()), HttpStatus.OK);
	}

	@GetMapping("moduleId/{moduleId}")
	public ResponseEntity<List<ClassDTO>> findClassesByModule(@PathVariable String moduleId) {
		return new ResponseEntity<>(service.findByModule(moduleId), HttpStatus.OK);
	}

}