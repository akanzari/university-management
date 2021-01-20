package com.esprit.service;

import java.util.Date;
import java.util.List;

import org.mapstruct.Named;

import com.esprit.domain.TeacherEntity;
import com.esprit.dto.teacher.CreateTeacherRequest;
import com.esprit.dto.teacher.TeacherDTO;

public interface TeacherService {

	void addTeacher(CreateTeacherRequest createTeacherRequest);

	TeacherDTO findBydId(String userId);

	@Named("findTeachersByIds")
	List<TeacherEntity> findTeachersByIds(List<String> teachers);

	List<TeacherDTO> findTeachers();

	List<TeacherDTO> findAllByUps(List<String> ups);

	List<TeacherDTO> searcheachers(String teacherId, int cin, String departement, String classs);

	void addDisponibilityToTeacher(String teacherId, Date supervisionDate, int supervisionHour,
			int supervisionDuration);

}