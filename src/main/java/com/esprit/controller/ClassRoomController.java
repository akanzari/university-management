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

import com.esprit.dto.request.CreateClassRoomRequest;
import com.esprit.dto.request.UpdateClassRoomRequest;
import com.esprit.dto.response.ClassRoomResponse;
import com.esprit.service.ClassRoomService;

@RestController
@RequestMapping("class-room")
public class ClassRoomController {

	private final ClassRoomService service;

	public ClassRoomController(ClassRoomService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ClassRoomResponse> createClassRoom(
			@RequestBody @Valid CreateClassRoomRequest createClassRoomRequest) {
		return new ResponseEntity<>(service.addClassRoom(createClassRoomRequest), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<ClassRoomResponse> updateClassRoom(
			@RequestBody @Valid UpdateClassRoomRequest updateClassRoomRequest) {
		return new ResponseEntity<>(service.updateClassRoom(updateClassRoomRequest), HttpStatus.CREATED);
	}

	@DeleteMapping("{classRoomId}")
	public ResponseEntity<Void> deleteClassRoom(@PathVariable String classRoomId) {
		service.deleteClassRoom(classRoomId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("{classRoomId}")
	public ResponseEntity<ClassRoomResponse> findClassRoom(@PathVariable String classRoomId) {
		return new ResponseEntity<>(service.findClassRoom(classRoomId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<ClassRoomResponse>> findClassRooms() {
		return new ResponseEntity<>(service.findClassRooms(), HttpStatus.OK);
	}

}