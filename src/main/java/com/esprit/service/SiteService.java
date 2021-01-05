package com.esprit.service;

import java.util.List;

import com.esprit.dto.request.CreateSiteRequest;
import com.esprit.dto.response.SiteResponse;

public interface SiteService {

	SiteResponse addSite(CreateSiteRequest createSiteRequest);

	void deleteSite(String siteId);

	SiteResponse findSite(String siteId);

	List<SiteResponse> findSites();

}