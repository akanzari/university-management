package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.ClassEntity;

public interface ClassRepository extends JpaRepository<ClassEntity, String> {

	ClassEntity findByCode(String code);

}