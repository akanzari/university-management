package com.esprit.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esprit.domain.RoomEntity;
import com.esprit.dto.room.RoomWithoutDisponibilityDTO;

public interface RoomRepository extends JpaRepository<RoomEntity, String> {
	
	public static String query = "SELECT * FROM salle sl where sl.classRoomId NOT IN\n" + 
			"(SELECT disp.fk_room FROM DISPONIBILITY disp where ?1 BETWEEN disp.STARTDATE and disp.ENDDATE and ?2 >= disp.startHour and ?2 < disp.endHour )\n" +
			"AND sl.bloc IN ?3";

	@Query("SELECT new com.esprit.dto.room.RoomWithoutDisponibilityDTO(r.classRoomId, r.label, r.capacity, r.pole, r.bloc) FROM RoomEntity as r")
	List<RoomWithoutDisponibilityDTO> findRoomsWithoutDisponibilities();

	@Query("FROM RoomEntity as r WHERE r.bloc = ?1 and r.disponibilities IS NULL")
	List<RoomEntity> findByBloc(String bloc);

	@Query(nativeQuery = true, value = query)
	List<RoomEntity> findAllByBlocs(Date effectDate, int hour, List<String> blocs);

	List<RoomEntity> findByClassRoomIdIn(List<String> roomIds);
	
	@Query(nativeQuery = true, value = "select DISTINCT bloc from salle where classRoomId IN ?1")
	List<String> findBlocByRooms(List<String> rooms);

}