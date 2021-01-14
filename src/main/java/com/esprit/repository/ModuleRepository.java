package com.esprit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esprit.domain.ModuleEntity;

public interface ModuleRepository extends JpaRepository<ModuleEntity, String> {
	
	ModuleEntity findByDesignation(String designation);
	
	/*@Query("FROM ModuleEntity module LEFT JOIN module.classs classs WHERE classs.classId = ?1")
	List<ModuleEntity> findByClass(String classId);*/

}