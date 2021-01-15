package com.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.domain.RoomEntity;

public interface RoomRepository extends JpaRepository<RoomEntity, String> {

	RoomEntity findByLabel(String label);

}