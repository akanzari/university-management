package com.esprit.service;

import java.util.List;

import org.mapstruct.Named;

import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.SpecialityDTO;

public interface SpecialityService {

	void addSpeciality(SpecialityDTO specialityDTO);

	@Named("findSpeciality")
	SpecialityEntity findSpeciality(String specialityId);

	List<SpecialityDTO> findSpecialities();

}