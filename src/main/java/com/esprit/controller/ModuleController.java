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

import com.esprit.dto.request.CreateModuleRequest;
import com.esprit.dto.request.UpdateModuleRequest;
import com.esprit.dto.response.ModuleResponse;
import com.esprit.service.ModuleService;

@RestController
@RequestMapping("module")
public class ModuleController {

	private final ModuleService service;

	public ModuleController(ModuleService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ModuleResponse> createModule(@RequestBody @Valid CreateModuleRequest createModuleRequest) {
		return new ResponseEntity<>(service.addModule(createModuleRequest), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<ModuleResponse> createModule(@RequestBody @Valid UpdateModuleRequest updateModuleRequest) {
		return new ResponseEntity<>(service.updateModule(updateModuleRequest), HttpStatus.CREATED);
	}

	@DeleteMapping("{moduleId}")
	public ResponseEntity<Void> deleteModule(@PathVariable String moduleId) {
		service.deleteModule(moduleId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("{moduleId}")
	public ResponseEntity<ModuleResponse> findModule(@PathVariable String moduleId) {
		return new ResponseEntity<>(service.findModule(moduleId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<ModuleResponse>> findModules() {
		return new ResponseEntity<>(service.findModules(), HttpStatus.OK);
	}

}