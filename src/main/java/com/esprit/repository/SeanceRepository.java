package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.SeanceEntity;

public interface SeanceRepository extends JpaRepository<SeanceEntity, String> {

}