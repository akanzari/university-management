package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.SiteEntity;
import com.esprit.dto.request.CreateSiteRequest;
import com.esprit.dto.response.SiteResponse;
import com.esprit.error.exception.EntityAlreadyExistsExeption;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.SiteRepository;
import com.esprit.service.SiteService;
import com.esprit.service.mapper.SiteMapper;

@Service
public class SiteServiceImpl implements SiteService {

	private final SiteRepository repository;

	private final SiteMapper mapper;

	public SiteServiceImpl(SiteRepository repository, SiteMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public SiteResponse addSite(CreateSiteRequest createSiteRequest) {
		SiteEntity siteEntity = mapper.createSiteRequestToSiteEntity(createSiteRequest);

		if (repository.findByCode(createSiteRequest.getCode()) != null) {
			throw new EntityAlreadyExistsExeption(SiteEntity.class, "code", createSiteRequest.getCode());
		}

		siteEntity = repository.save(siteEntity);
		return mapper.siteEntitToSiteResponse(siteEntity);
	}

	@Override
	public void deleteSite(String siteId) {
		if (findSite(siteId) != null) {
			repository.deleteById(siteId);
		}
	}

	@Override
	public SiteResponse findSite(String siteId) {
		SiteResponse result = null;
		Optional<SiteEntity> siteEntityOptional = repository.findById(siteId);
		if (siteEntityOptional.isPresent()) {
			result = mapper.siteEntitToSiteResponse(siteEntityOptional.get());
		} else {
			throw new EntityNotFoundException(SiteEntity.class, "id", siteId);
		}
		return result;
	}

	@Override
	public List<SiteResponse> findSites() {
		return mapper.siteEntitiesToSiteResponse(repository.findAll());
	}

}