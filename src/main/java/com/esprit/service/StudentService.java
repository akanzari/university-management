package com.esprit.service;

import com.esprit.dto.user.CreateStudentRequest;
import com.esprit.dto.user.StudentResponse;

public interface StudentService {

	void addStudent(CreateStudentRequest createStudentRequest);

	StudentResponse findBydId(String userId);

}