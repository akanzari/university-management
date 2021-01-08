package com.esprit.service;

import com.esprit.dto.request.CreateTeacherRequest;
import com.esprit.dto.response.TeacherResponse;

public interface TeacherService {

	void addTeacher(CreateTeacherRequest createTeacherRequest);

	TeacherResponse findBydId(String userId);

}