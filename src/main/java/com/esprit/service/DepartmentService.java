package com.esprit.service;

import java.util.List;

import org.mapstruct.Named;

import com.esprit.domain.DepartmentEntity;
import com.esprit.dto.DepartmentDTO;

public interface DepartmentService {

	void addDepartment(DepartmentDTO departmentDTO);

	@Named("findDepartment")
	DepartmentEntity findDepartment(String departmentId);
	
	List<DepartmentDTO> findDepartments();

}