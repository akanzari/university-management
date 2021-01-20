package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.ClassEntity;
import com.esprit.repository.search.ClassRepositoryCustom;

public interface ClassRepository extends JpaRepository<ClassEntity, String>, ClassRepositoryCustom {

	ClassEntity findByLabel(String label);

}