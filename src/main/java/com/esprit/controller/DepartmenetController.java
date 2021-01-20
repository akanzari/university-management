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

import com.esprit.dto.DepartmentDTO;
import com.esprit.service.DepartmentService;

@RestController
@RequestMapping("department")
public class DepartmenetController {

	private final DepartmentService service;

	public DepartmenetController(DepartmentService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> createDepartment(@RequestBody @Valid DepartmentDTO departmentDTO) {
		service.addDepartment(departmentDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<DepartmentDTO>> findDepartments() {
		return new ResponseEntity<>(service.findDepartments(), HttpStatus.OK);
	}

}