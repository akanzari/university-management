package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.ModuleEntity;

public interface ModuleRepository extends JpaRepository<ModuleEntity, String> {

}