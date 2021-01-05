package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.SiteEntity;

public interface SiteRepository extends JpaRepository<SiteEntity, String> {

	SiteEntity findByCode(String code);

}