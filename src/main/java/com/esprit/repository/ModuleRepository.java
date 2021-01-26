package com.esprit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esprit.domain.ModuleEntity;
import com.esprit.dto.module.ModuleWithoutAssignClassesDTO;

public interface ModuleRepository extends JpaRepository<ModuleEntity, String> {

	@Query("SELECT new com.esprit.dto.module.ModuleWithoutAssignClassesDTO(m.moduleId, m.designation) FROM ModuleEntity as m")
	List<ModuleWithoutAssignClassesDTO> findAllWithoutAssignClasses();
	
}