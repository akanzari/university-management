package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.DepartmentEntity;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, String> {

	DepartmentEntity findByCode(String code);

}