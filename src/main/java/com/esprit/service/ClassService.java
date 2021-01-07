package com.esprit.service;

import java.util.List;

import com.esprit.dto.request.CreateClassRequest;
import com.esprit.dto.request.UpdateClassRequest;
import com.esprit.dto.response.ClassResponse;

public interface ClassService {

	ClassResponse addClass(CreateClassRequest createClassRequest);
	
	ClassResponse updateClass(UpdateClassRequest updateClassRequest);

	void deleteClass(String classId);
	
	ClassResponse findClasse(String classId);

	List<ClassResponse> findClasses();

}