package com.esprit.service;

import java.util.List;

import org.mapstruct.Named;

import com.esprit.domain.ModuleEntity;
import com.esprit.dto.module.AssignClassModuleDTO;
import com.esprit.dto.module.CreateModuleRequest;
import com.esprit.dto.module.ModuleDTO;

public interface ModuleService {

	void addModule(CreateModuleRequest createModuleRequest);

	void deleteModule(String moduleId);

	ModuleDTO findModule(String moduleId);

	@Named("findModuleById")
	ModuleEntity findModuleById(String moduleId);

	List<ModuleDTO> findModules();

	List<ModuleDTO> findAllWithoutAssignClasses();

	List<AssignClassModuleDTO> findAssignClassesByModule(String moduleId);

	List<ModuleDTO> findModulesByClass(String classId);

}