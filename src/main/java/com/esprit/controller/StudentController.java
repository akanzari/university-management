package com.esprit.controller;

import java.security.Principal;
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

import com.esprit.dto.student.CreateStudentRequest;
import com.esprit.dto.student.StudentDTO;
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

	@PutMapping
	public ResponseEntity<Void> updateStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest) {
		service.addStudent(createStudentRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<StudentDTO>> findStudents() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/current-student")
	public ResponseEntity<StudentDTO> findCurrentStudent(Principal principal) {
		return new ResponseEntity<>(service.findByPrincipal(principal), HttpStatus.OK);
	}

	@DeleteMapping("{studentId}")
	public ResponseEntity<Void> deleteStudent(@PathVariable String studentId) {
		service.deleteStudent(studentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}