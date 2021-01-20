package com.esprit.service;

import java.util.List;

import org.mapstruct.Named;

import com.esprit.domain.SeanceEntity;
import com.esprit.dto.seance.CreateSeanceRequest;
import com.esprit.dto.seance.SeanceDTO;

public interface SeanceService {

	void addSeance(CreateSeanceRequest createSeanceRequest);

	@Named("findSeance")
	SeanceEntity findSeance(String seanceId);

	List<SeanceDTO> findSeances();

}