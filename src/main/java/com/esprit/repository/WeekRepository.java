package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.WeekEntity;

public interface WeekRepository extends JpaRepository<WeekEntity, String> {

}