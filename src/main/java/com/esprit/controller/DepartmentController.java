package com.esprit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.dto.request.CreateDepartmentRequest;
import com.esprit.dto.response.DepartmentResponse;
import com.esprit.service.DepartmentService;

@RestController
@RequestMapping("departement")
public class DepartmentController {

	private final DepartmentService service;

	public DepartmentController(DepartmentService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<DepartmentResponse> createDepartement(
			@RequestBody @Valid CreateDepartmentRequest createDepartementRequest) {
		return new ResponseEntity<>(service.addDepartement(createDepartementRequest), HttpStatus.CREATED);
	}

	@DeleteMapping("{departementId}")
	public ResponseEntity<Void> deleteDepartement(@PathVariable String departementId) {
		service.deleteDepartement(departementId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("{departementId}")
	public ResponseEntity<DepartmentResponse> findDepartement(@PathVariable String departementId) {
		return new ResponseEntity<>(service.findDepartement(departementId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<DepartmentResponse>> findDepartements() {
		return new ResponseEntity<>(service.findDepartements(), HttpStatus.OK);
	}

}