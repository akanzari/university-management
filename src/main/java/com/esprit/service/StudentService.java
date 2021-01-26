package com.esprit.service;

import java.security.Principal;
import java.util.List;

import com.esprit.dto.student.CreateStudentRequest;
import com.esprit.dto.student.StudentDTO;

public interface StudentService {

	void addStudent(CreateStudentRequest createStudentRequest);

	void updateStudent(CreateStudentRequest createStudentRequest);

	void deleteStudent(String studentId);

	StudentDTO findByPrincipal(Principal principal);

	List<StudentDTO> findAll();

}