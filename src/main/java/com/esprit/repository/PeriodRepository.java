package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.PeriodEntity;

public interface PeriodRepository extends JpaRepository<PeriodEntity, String> {

}