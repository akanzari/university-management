package com.esprit.service;

import java.util.List;

import org.mapstruct.Named;

import com.esprit.domain.SemesterEntity;
import com.esprit.dto.SemesterDTO;

public interface SemesterService {

	void addSemester(SemesterDTO semesterDTO);

	@Named("findSemester")
	SemesterEntity findSemester(String semesterId);

	List<SemesterDTO> findSemesters();

}