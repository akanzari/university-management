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
import com.esprit.dto.room.CreateRoomRequest;
import com.esprit.dto.room.DisponibilityDTO;
import com.esprit.dto.room.FilterAvailableRoomDTO;
import com.esprit.dto.room.RoomDTO;
import com.esprit.service.RoomService;

@RestController
@RequestMapping("room")
public class RoomController {

	private final RoomService service;

	public RoomController(RoomService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> createClassRoom(@RequestBody @Valid CreateRoomRequest createRoomRequest) {
		service.addClassRoom(createRoomRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Void> updateClassRoom(@RequestBody @Valid CreateRoomRequest createRoomRequest) {
		service.updateClassRoom(createRoomRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("{classRoomId}")
	public ResponseEntity<Void> deleteClassRoom(@PathVariable String classRoomId) {
		service.deleteClassRoom(classRoomId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("disponibility/{classRoomId}")
	public ResponseEntity<Void> createDisponibility(@PathVariable String classRoomId,
			@RequestBody @Valid CreateDisponibilityRequest createDispoibilityRequest) {
		service.addDisponibility(classRoomId, createDispoibilityRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("bloc/{blocId}")
	public ResponseEntity<List<RoomDTO>> findByBloc(@PathVariable String blocId) {
		return new ResponseEntity<>(service.findByBloc(blocId), HttpStatus.OK);
	}

	@GetMapping("{classRoomId}")
	public ResponseEntity<RoomDTO> findClassRoom(@PathVariable String classRoomId) {
		return new ResponseEntity<>(service.findClassRoom(classRoomId), HttpStatus.OK);
	}

	@PostMapping("blocs")
	public ResponseEntity<List<RoomDTO>> findAllByBlocs(@RequestBody FilterAvailableRoomDTO filterAvailableRoomDTO) {
		return new ResponseEntity<>(service.findAllByBlocs(filterAvailableRoomDTO.getEffectDate(),
				filterAvailableRoomDTO.getHour(), filterAvailableRoomDTO.getBlocs()), HttpStatus.OK);
	}

	@GetMapping("without-disponibilities")
	public ResponseEntity<List<RoomDTO>> findRoomsWithoutDisponibilities() {
		return new ResponseEntity<>(service.findRoomsWithoutDisponibilities(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<RoomDTO>> findRooms() {
		return new ResponseEntity<>(service.findRooms(), HttpStatus.OK);
	}

	@PutMapping("disponibility")
	public ResponseEntity<Void> updateDisponibility(
			@RequestBody @Valid CreateDisponibilityRequest createDispoibilityRequest) {
		service.updateDisponibility(createDispoibilityRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("disponibility/{disponibilityId}")
	public ResponseEntity<Void> deleteDisponibility(@PathVariable String disponibilityId) {
		service.deleteDisponibility(disponibilityId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("disponibility/{classRoomId}")
	public ResponseEntity<List<DisponibilityDTO>> findDisponibilitiesByRoomId(@PathVariable String classRoomId) {
		return new ResponseEntity<>(service.findDisponibilitiesByRoomId(classRoomId), HttpStatus.OK);
	}

}