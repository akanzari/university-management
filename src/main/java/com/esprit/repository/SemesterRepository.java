package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.SemesterEntity;

public interface SemesterRepository extends JpaRepository<SemesterEntity, String> {

}