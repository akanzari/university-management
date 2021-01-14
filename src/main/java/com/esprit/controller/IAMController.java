package com.esprit.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

	@PostMapping(path = "/user")
	public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
		return new ResponseEntity<>(iamService.addUser(createUserRequest), HttpStatus.CREATED);
	}

	@PutMapping(path = "/user")
	public ResponseEntity<Void> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
		iamService.updateUser(updateUserRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping(path = "/user/disabled/{userId}")
	public ResponseEntity<Void> disabledUser(@PathVariable String userId) {
		iamService.disabledUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping(path = "/user/enable/{userId}")
	public ResponseEntity<Void> enabledUser(@PathVariable String userId) {
		iamService.enabledUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(path = "/user/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
		iamService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/users")
	public ResponseEntity<List<UserResponse>> findUsers() {
		return new ResponseEntity<>(iamService.findUsers(), HttpStatus.OK);
	}

	@GetMapping(path = "/users/{role}")
	public ResponseEntity<List<SpecificUserResponse>> findUsersByRole(@PathVariable String role) {
		return new ResponseEntity<>(iamService.findUsersByRole(role), HttpStatus.OK);
	}

	@GetMapping(path = "/user/{email}")
	public ResponseEntity<UserResponse> findUsers(@PathVariable String email) {
		return new ResponseEntity<>(iamService.findUser(email), HttpStatus.OK);
	}

	@GetMapping(path = "/current-user")
	public ResponseEntity<UserResponse> findCurrentUser(Principal principal) {
		return new ResponseEntity<>(iamService.findCurrentUser(principal), HttpStatus.OK);
	}

	@GetMapping(path = "/roles")
	public ResponseEntity<List<String>> findRoles() {
		return new ResponseEntity<>(iamService.findRoles(), HttpStatus.OK);
	}

}