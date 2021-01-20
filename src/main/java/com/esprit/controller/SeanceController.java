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

import com.esprit.dto.seance.CreateSeanceRequest;
import com.esprit.dto.seance.SeanceDTO;
import com.esprit.service.SeanceService;

@RestController
@RequestMapping("seance")
public class SeanceController {

	private final SeanceService service;

	public SeanceController(SeanceService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> createSeance(@RequestBody @Valid CreateSeanceRequest createSeanceRequest) {
		service.addSeance(createSeanceRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<SeanceDTO>> findSeances() {
		return new ResponseEntity<>(service.findSeances(), HttpStatus.OK);
	}

}