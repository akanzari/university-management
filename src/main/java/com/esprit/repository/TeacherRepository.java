package com.esprit.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esprit.domain.TeacherEntity;
import com.esprit.repository.search.TeacherRepositoryCustom;

public interface TeacherRepository extends JpaRepository<TeacherEntity, String>, TeacherRepositoryCustom {
	
	public static String query = "SELECT * FROM ESP_ENSEIGNANT es where es.teacherId NOT IN\n" + 
			"(SELECT disp.fk_room FROM DISPONIBILITY disp where ?1 BETWEEN disp.STARTDATE and disp.ENDDATE and ?2 >= disp.startHour and ?2 < disp.endHour )\n" +
			"AND es.up IN ?3";

	@Query(nativeQuery = true, value = query)
	List<TeacherEntity> findAllByUps(Date effectDate, int hour, List<String> ups);

	List<TeacherEntity> findByTeacherIdIn(List<String> teacherIds);
	
	@Query(nativeQuery = true, value = "select DISTINCT up from esp_enseignant where teacherId IN ?1")
	List<String> findUpByTeachers(List<String> teachers);

}