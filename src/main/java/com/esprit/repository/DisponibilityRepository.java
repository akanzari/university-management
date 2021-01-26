package com.esprit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esprit.domain.DisponibilityEntity;

public interface DisponibilityRepository extends JpaRepository<DisponibilityEntity, String> {

	@Query(nativeQuery = true, value = "select * from DISPONIBILITY ds inner join SALLE s on s.CLASSROOMID = ds.FK_ROOM and s.CLASSROOMID = ?1")
	List<DisponibilityEntity> findAllByRoom(String roomId);

	@Query(nativeQuery = true, value = "select * from DISPONIBILITY ds inner join ESP_ENSEIGNANT s on s.TEACHERID = ds.fk_teacher and s.teacherId = ?1")
	List<DisponibilityEntity> findAllByTeacher(String teacherId);
	
}