package com.esprit.repository.search;

import java.util.List;

import com.esprit.domain.ClassEntity;

public interface ClassRepositoryCustom {

	List<ClassEntity> searchConventions(String name, String email, String speciality, int nbrStudents);

}