package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.ExamEntity;

public interface ExamRepository extends JpaRepository<ExamEntity, String> {

}