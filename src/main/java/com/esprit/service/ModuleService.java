package com.esprit.service;

import java.util.List;

import com.esprit.dto.request.CreateModuleRequest;
import com.esprit.dto.request.UpdateModuleRequest;
import com.esprit.dto.response.ModuleResponse;

public interface ModuleService {

	ModuleResponse addModule(CreateModuleRequest createModuleRequest);

	ModuleResponse updateModule(UpdateModuleRequest updateModuleRequest);

	void deleteModule(String moduleId);

	ModuleResponse findModule(String moduleId);

	List<ModuleResponse> findModules();

}