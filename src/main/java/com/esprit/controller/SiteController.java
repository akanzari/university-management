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

import com.esprit.dto.request.CreateSiteRequest;
import com.esprit.dto.response.SiteResponse;
import com.esprit.service.SiteService;

@RestController
@RequestMapping("site")
public class SiteController {

	private final SiteService service;

	public SiteController(SiteService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<SiteResponse> createSite(@RequestBody @Valid CreateSiteRequest createSiteRequest) {
		return new ResponseEntity<>(service.addSite(createSiteRequest), HttpStatus.CREATED);
	}

	@DeleteMapping("{siteId}")
	public ResponseEntity<Void> deleteSite(@PathVariable String siteId) {
		service.deleteSite(siteId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("{siteId}")
	public ResponseEntity<SiteResponse> findSite(@PathVariable String siteId) {
		return new ResponseEntity<>(service.findSite(siteId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<SiteResponse>> findSites() {
		return new ResponseEntity<>(service.findSites(), HttpStatus.OK);
	}

}