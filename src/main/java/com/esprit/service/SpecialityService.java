package com.esprit.service;

import java.util.List;

import com.esprit.dto.request.CreateSpecialityRequest;
import com.esprit.dto.response.SpecialityResponse;

public interface SpecialityService {

	SpecialityResponse addSpeciality(CreateSpecialityRequest createSpecialityRequest);

	void deleteSpeciality(String specialityId);

	SpecialityResponse findSpeciality(String specialityId);

	List<SpecialityResponse> findSpecialities();

}