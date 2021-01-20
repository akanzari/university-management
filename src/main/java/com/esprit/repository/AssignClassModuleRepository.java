package com.esprit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esprit.domain.AssignClassModuleEntity;

public interface AssignClassModuleRepository extends JpaRepository<AssignClassModuleEntity, String> {
	
	@Query(nativeQuery = true, value = "select * from esp_module_class mc inner join esp_module m on m.moduleId = mc.fk_module and m.moduleId = ?1")
	List<AssignClassModuleEntity> findAllByModule(String moduleId);

}