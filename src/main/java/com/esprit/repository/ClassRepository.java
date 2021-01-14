package com.esprit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esprit.domain.ClassEntity;

public interface ClassRepository extends JpaRepository<ClassEntity, String> {

	ClassEntity findByLabel(String label);

	//@Query("FROM ClassEntity classs INNER JOIN classs.modules module WHERE module.moduleId = ?1")
	//List<ClassEntity> findByModule(String moduleId);

}