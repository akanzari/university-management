package com.esprit.service;

import java.util.List;

import org.mapstruct.Named;

import com.esprit.domain.ClassEntity;
import com.esprit.dto.classes.ClassDTO;
import com.esprit.dto.classes.CreateClassRequest;

public interface ClassService {

	void addClass(CreateClassRequest createClassRequest);

	void updateClass(CreateClassRequest createClassRequest);

	void deleteClass(String classId);

	ClassDTO findClass(String classId);

	@Named("findClassesByIds")
	List<ClassEntity> findClassesByIds(List<String> classes);
	
	List<ClassDTO> findByModule(String module);

	List<ClassDTO> findClasses();

	List<ClassDTO> searchClasses(String name, String email, String speciality, int nbrStudents);

}