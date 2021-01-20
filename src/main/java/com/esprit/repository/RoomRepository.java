package com.esprit.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esprit.domain.RoomEntity;
import com.esprit.dto.room.RoomWithoutDisponibilityDTO;

public interface RoomRepository extends JpaRepository<RoomEntity, String> {
	
	public static String query = "SELECT * FROM salle s \n" + 
			"  LEFT JOIN DISPONIBILITY_SALLE ds ON ds.fk_room = s.classRoomId\n" + 
			"  LEFT JOIN SEMAINE sm ON ds.fk_week = sm.weekId\n" + 
			"  LEFT JOIN SEANCE sc ON ds.fk_seance = sc.seanceId\n" + 
			"  WHERE (?1 BETWEEN sm.STARTDATE AND sm.ENDDATE) AND (?2 BETWEEN sc.startHour AND sc.endHour)\n" + 
			"  AND s.bloc IN ?3";

	@Query("SELECT new com.esprit.dto.room.RoomWithoutDisponibilityDTO(r.classRoomId, r.label, r.capacity, r.pole, r.bloc) FROM RoomEntity as r")
	List<RoomWithoutDisponibilityDTO> findRoomsWithoutDisponibilities();

	@Query("FROM RoomEntity as r WHERE r.bloc = ?1 and r.disponibilities IS NULL")
	List<RoomEntity> findByBloc(String bloc);

	@Query(nativeQuery = true, value = query)
	List<RoomEntity> findAllByBlocs(Date effectDate, int hour, List<String> blocs);

}