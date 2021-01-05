package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.esprit.domain.SiteEntity;
import com.esprit.dto.request.CreateSiteRequest;
import com.esprit.dto.response.SiteResponse;

@Mapper
public interface SiteMapper {

	SiteEntity createSiteRequestToSiteEntity(CreateSiteRequest createSiteRequest);

	SiteResponse siteEntitToSiteResponse(SiteEntity siteEntity);

	List<SiteResponse> siteEntitiesToSiteResponse(List<SiteEntity> siteEntities);

}