package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.DepartmentEntity;

public interface DepartementReposiroty extends JpaRepository<DepartmentEntity, String> {

}