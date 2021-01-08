package com.esprit.service;

import com.esprit.dto.request.CreateStudentRequest;
import com.esprit.dto.response.StudentResponse;

public interface StudentService {

	void addStudent(CreateStudentRequest createStudentRequest);

	StudentResponse findBydId(String userId);

}