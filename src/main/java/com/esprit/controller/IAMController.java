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

import com.esprit.dto.request.CreateUserRequest;
import com.esprit.dto.request.UpdateUserRequest;
import com.esprit.dto.response.SpecificUserResponse;
import com.esprit.dto.response.UserResponse;
import com.esprit.service.IAMService;

@RestController
@RequestMapping("iam")
public class IAMController {

	private final IAMService iamService;

	public IAMController(IAMService iamService) {
		this.iamService = iamService;
	}

	@PostMapping(path = "/create-user")
	public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
		iamService.addUser(createUserRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping(path = "/update-user")
	public ResponseEntity<Void> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
		iamService.updateUser(updateUserRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(path = "/delete-user/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
		iamService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/find-users")
	public ResponseEntity<List<UserResponse>> findUsers() {
		return new ResponseEntity<>(iamService.findUsers(), HttpStatus.OK);
	}

	@GetMapping(path = "/find-users/{role}")
	public ResponseEntity<List<SpecificUserResponse>> findUsersByRole(@PathVariable String role) {
		return new ResponseEntity<>(iamService.findUsersByRole(role), HttpStatus.OK);
	}

	@GetMapping(path = "/find-user/{username}")
	public ResponseEntity<UserResponse> findUsers(@PathVariable String username) {
		return new ResponseEntity<>(iamService.findUser(username), HttpStatus.OK);
	}

	@GetMapping(path = "/current-user")
	public ResponseEntity<UserResponse> findCurrentUser(Principal principal) {
		return new ResponseEntity<>(iamService.findCurrentUser(principal), HttpStatus.OK);
	}

	@GetMapping(path = "/find-roles")
	public ResponseEntity<List<String>> findRoles() {
		return new ResponseEntity<>(iamService.findRoles(), HttpStatus.OK);
	}

}