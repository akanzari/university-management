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

import com.esprit.dto.request.CreateSpecialityRequest;
import com.esprit.dto.response.SpecialityResponse;
import com.esprit.service.SpecialityService;

@RestController
@RequestMapping("speciality")
public class SpecialityController {

	private final SpecialityService service;

	public SpecialityController(SpecialityService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<SpecialityResponse> createSpeciality(
			@RequestBody @Valid CreateSpecialityRequest createSpecialityRequest) {
		return new ResponseEntity<>(service.addSpeciality(createSpecialityRequest), HttpStatus.CREATED);
	}

	@DeleteMapping("{specialityId}")
	public ResponseEntity<Void> deleteSpeciality(@PathVariable String specialityId) {
		service.deleteSpeciality(specialityId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("{specialityId}")
	public ResponseEntity<SpecialityResponse> findSite(@PathVariable String specialityId) {
		return new ResponseEntity<>(service.findSpecialitie(specialityId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<SpecialityResponse>> findSpecialities() {
		return new ResponseEntity<>(service.findSpecialities(), HttpStatus.OK);
	}

}