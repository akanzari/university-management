package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.SiteEntity;
import com.esprit.dto.request.CreateSiteRequest;
import com.esprit.dto.response.SiteResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SiteMapper {

	SiteMapper INSTANCE = Mappers.getMapper(SiteMapper.class);

	SiteEntity createSiteRequestToSiteEntity(CreateSiteRequest createSiteRequest);

	SiteResponse siteEntitToSiteResponse(SiteEntity siteEntity);

	List<SiteResponse> siteEntitiesToSiteResponse(List<SiteEntity> siteEntities);

}