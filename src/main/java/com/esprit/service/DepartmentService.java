package com.esprit.service;

import java.util.List;

import com.esprit.dto.request.CreateDepartmentRequest;
import com.esprit.dto.response.DepartmentResponse;

public interface DepartmentService {

	DepartmentResponse addDepartement(CreateDepartmentRequest createDepartementRequest);

	void deleteDepartement(String departementId);
	
	DepartmentResponse findDepartement(String departementId);

	List<DepartmentResponse> findDepartements();

}