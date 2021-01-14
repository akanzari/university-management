package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.ClassRoomEntity;

public interface ClassRoomRepository extends JpaRepository<ClassRoomEntity, String> {

	ClassRoomEntity findByLabel(String label);

}