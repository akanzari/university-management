package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.SpecialityEntity;

public interface SpecialityRepository extends JpaRepository<SpecialityEntity, String> {

}