package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.TeacherEntity;

public interface TeacherRepository extends JpaRepository<TeacherEntity, String> {

}