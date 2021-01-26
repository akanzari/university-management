package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, String> {
	
	StudentEntity findByCin(int cin);
	
	StudentEntity findByEmail(String email);

}