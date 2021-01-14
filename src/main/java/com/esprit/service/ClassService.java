package com.esprit.service;

import java.util.List;

import com.esprit.dto.ClassDTO;

public interface ClassService {

	void addClass(ClassDTO classDTO);

	void updateClass(ClassDTO classDTO);

	void deleteClass(String classId);

	ClassDTO findClass(String classId);

	List<ClassDTO> findClasses();

	List<ClassDTO> findClassesByModule(String moduleId);

}