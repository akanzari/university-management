package com.esprit.service;

import java.util.List;

import com.esprit.dto.request.modules.CreateModuleRequest;
import com.esprit.dto.response.ModuleResponse;

public interface ModuleService {

	void addModule(CreateModuleRequest createModuleRequest);

	void deleteModule(String moduleId);

	ModuleResponse findModule(String moduleId);

	List<ModuleResponse> findModules();

	List<ModuleResponse> findModulesByClass(String classId);

}