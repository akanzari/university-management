package com.esprit.service;

import java.util.List;

import com.esprit.dto.TeacherDTO;

public interface TeacherService {

	void addTeacher(TeacherDTO teacherDTO);

	TeacherDTO findBydId(String userId);

	List<TeacherDTO> findTeachers();

}