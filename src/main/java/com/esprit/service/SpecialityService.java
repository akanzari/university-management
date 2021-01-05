package com.esprit.service;

import java.util.List;

import org.mapstruct.Named;

import com.esprit.dto.request.CreateSpecialityRequest;
import com.esprit.dto.response.SpecialityResponse;

public interface SpecialityService {

	SpecialityResponse addSpeciality(CreateSpecialityRequest createSpecialityRequest);

	void deleteSpeciality(String specialityId);

	@Named("stringToEventTime")
	SpecialityResponse findSpecialitie(String specialityId);

	List<SpecialityResponse> findSpecialities();

}