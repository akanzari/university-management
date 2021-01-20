package com.esprit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esprit.domain.TeacherEntity;
import com.esprit.repository.search.TeacherRepositoryCustom;

public interface TeacherRepository extends JpaRepository<TeacherEntity, String>, TeacherRepositoryCustom {

	@Query("FROM TeacherEntity t WHERE t.up IN ?1")
	List<TeacherEntity> findAllByUps(List<String> ups);

}