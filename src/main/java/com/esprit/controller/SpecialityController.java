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

import com.esprit.dto.SpecialityDTO;
import com.esprit.service.SpecialityService;

@RestController
@RequestMapping("speciality")
public class SpecialityController {

	private final SpecialityService service;

	public SpecialityController(SpecialityService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> createSpeciality(@RequestBody @Valid SpecialityDTO specialityDTO) {
		service.addSpeciality(specialityDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<SpecialityDTO>> findSpecialities() {
		return new ResponseEntity<>(service.findSpecialities(), HttpStatus.OK);
	}

}