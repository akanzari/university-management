package com.esprit.repository.search;

import java.util.List;

import com.esprit.domain.TeacherEntity;

public interface TeacherRepositoryCustom {

	List<TeacherEntity> searcheachers(String teacherId, int cin, String departement, String classs);

}