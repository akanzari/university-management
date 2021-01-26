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

import com.esprit.dto.CreateDisponibilityRequest;
import com.esprit.dto.room.DisponibilityDTO;
import com.esprit.service.DisponibilityService;

@RestController
@RequestMapping("disponibility")
public class DisponibilityController {

	private final DisponibilityService service;

	public DisponibilityController(DisponibilityService service) {
		this.service = service;
	}

	@PostMapping("room/{roomId}")
	public ResponseEntity<Void> createDisponibilityToRoom(@PathVariable String roomId,
			@RequestBody @Valid CreateDisponibilityRequest createDispoibilityRequest) {
		service.addDisponibilityToRoom(roomId, createDispoibilityRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("teacher/{teacherId}")
	public ResponseEntity<Void> createDisponibilityToTeacher(@PathVariable String teacherId,
			@RequestBody @Valid CreateDisponibilityRequest createDispoibilityRequest) {
		service.addDisponibilityToTeacher(teacherId, createDispoibilityRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Void> updateDisponibility(
			@RequestBody @Valid CreateDisponibilityRequest createDispoibilityRequest) {
		service.updateDisponibility(createDispoibilityRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("teacher/{teacherId}/{disponibilityId}")
	public ResponseEntity<Void> deleteDisponibilityFromTeacher(@PathVariable String teacherId,
			@PathVariable String disponibilityId) {
		service.deleteDisponibilityFromTeacher(teacherId, disponibilityId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("room/{roomId}/{disponibilityId}")
	public ResponseEntity<Void> deleteDisponibilityFromRoom(@PathVariable String roomId,
			@PathVariable String disponibilityId) {
		service.deleteDisponibilityFromRoom(roomId, disponibilityId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("teacher/{teacherId}")
	public ResponseEntity<List<DisponibilityDTO>> findAllByTeacher(@PathVariable String teacherId) {
		return new ResponseEntity<>(service.findAllByTeacher(teacherId), HttpStatus.OK);
	}

	@GetMapping("room/{roomId}")
	public ResponseEntity<List<DisponibilityDTO>> findAllByRoom(@PathVariable String roomId) {
		return new ResponseEntity<>(service.findAllByRoom(roomId), HttpStatus.OK);
	}

}