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

import com.esprit.dto.module.AssignClassModuleDTO;
import com.esprit.dto.module.CreateModuleRequest;
import com.esprit.dto.module.ModuleDTO;
import com.esprit.service.ModuleService;

@RestController
@RequestMapping("module")
public class ModuleController {

	private final ModuleService service;

	public ModuleController(ModuleService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> createModule(@RequestBody @Valid CreateModuleRequest createModuleRequest) {
		service.addModule(createModuleRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("{moduleId}")
	public ResponseEntity<Void> deleteModule(@PathVariable String moduleId) {
		service.deleteModule(moduleId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("{moduleId}")
	public ResponseEntity<ModuleDTO> findModule(@PathVariable String moduleId) {
		return new ResponseEntity<>(service.findModule(moduleId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<ModuleDTO>> findModules() {
		return new ResponseEntity<>(service.findModules(), HttpStatus.OK);
	}

	@GetMapping("assign-classes/{moduleId}")
	public ResponseEntity<List<AssignClassModuleDTO>> findAssignClassesByModule(@PathVariable String moduleId) {
		return new ResponseEntity<>(service.findAssignClassesByModule(moduleId), HttpStatus.OK);
	}

	@GetMapping("without-classes")
	public ResponseEntity<List<ModuleDTO>> findAllWithoutAssignClasses() {
		return new ResponseEntity<>(service.findAllWithoutAssignClasses(), HttpStatus.OK);
	}

	@GetMapping("classId/{classId}")
	public ResponseEntity<List<ModuleDTO>> findModulesByClass(@PathVariable String classId) {
		return new ResponseEntity<>(service.findModulesByClass(classId), HttpStatus.OK);
	}

}