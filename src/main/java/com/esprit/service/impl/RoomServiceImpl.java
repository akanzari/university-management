package com.esprit.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.esprit.domain.DisponibilityEntity;
import com.esprit.domain.RoomEntity;
import com.esprit.dto.CreateDisponibilityRequest;
import com.esprit.dto.room.CreateRoomRequest;
import com.esprit.dto.room.DisponibilityDTO;
import com.esprit.dto.room.RoomDTO;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.DisponibilityRepository;
import com.esprit.repository.RoomRepository;
import com.esprit.service.RoomService;
import com.esprit.service.mapper.RoomMapper;

@Service
public class RoomServiceImpl implements RoomService {

	private final RoomRepository classRoomRepository;

	private final DisponibilityRepository disponibilityRepository;

	private final RoomMapper mapper;

	public RoomServiceImpl(RoomRepository classRoomRepository, DisponibilityRepository disponibilityRepository,
			RoomMapper mapper) {
		this.classRoomRepository = classRoomRepository;
		this.disponibilityRepository = disponibilityRepository;
		this.mapper = mapper;
	}

	@Override
	public void addClassRoom(CreateRoomRequest createRoomRequest) {
		RoomEntity classRoomEntity = mapper.createRoomRequestToClassRoomEntity(createRoomRequest);
		if (CollectionUtils.isNotEmpty(classRoomEntity.getDisponibilities())) {
			Map<String, Integer> namesMap = new HashMap<>(6);
			namesMap.put("lundi", 0);
			namesMap.put("mardi", 1);
			namesMap.put("mercredi", 2);
			namesMap.put("jeudi", 3);
			namesMap.put("vendredi", 4);
			namesMap.put("samedi", 5);
			classRoomEntity.getDisponibilities().forEach(disp -> {
				if (disp.getWeek() != null && !StringUtils.isBlank(disp.getDay())) {
					disp.getDay().toUpperCase();
					Calendar c = Calendar.getInstance();
					c.setTime(disp.getWeek().getStartDate());
					c.add(Calendar.DATE, namesMap.get(disp.getDay()));
					disp.setExactDate(c.getTime());
				}
			});
			classRoomEntity.addDisponibilities(classRoomEntity.getDisponibilities());
		}
		classRoomRepository.save(classRoomEntity);
	}

	@Override
	public void updateClassRoom(CreateRoomRequest createRoomRequest) {
		RoomEntity classRoomEntity;
		Optional<RoomEntity> classRoomEntityOptional = classRoomRepository.findById(createRoomRequest.getClassRoomId());
		if (classRoomEntityOptional.isPresent()) {
			classRoomEntity = mapper.updateRoomRequestToClassRoomEntity(createRoomRequest);
			classRoomRepository.save(classRoomEntity);
		} else {
			throw new EntityNotFoundException(RoomEntity.class, "Id", createRoomRequest.getClassRoomId());
		}
	}

	@Override
	public void deleteClassRoom(String classRoomId) {
		if (findClassRoom(classRoomId) != null) {
			classRoomRepository.deleteById(classRoomId);
		}
	}

	@Override
	public RoomDTO findClassRoom(String classRoomId) {
		RoomDTO result = null;
		Optional<RoomEntity> classRoomEntityOptional = classRoomRepository.findById(classRoomId);
		if (classRoomEntityOptional.isPresent()) {
			result = mapper.classRoomEntityToClassRoomResponse(classRoomEntityOptional.get());
		} else {
			throw new EntityNotFoundException(RoomEntity.class, "id", classRoomId);
		}
		return result;
	}

	@Override
	public List<RoomDTO> findByBloc(String bloc) {
		return mapper.classRoomEntitiesToClassRoomResponse(classRoomRepository.findByBloc(bloc));
	}

	@Override
	public List<RoomDTO> findRooms() {
		return mapper.classRoomEntitiesToClassRoomResponse(classRoomRepository.findAll());
	}

	@Override
	public List<RoomDTO> findAllByBlocs(Date effectDate, int hour, List<String> blocs) {
		return mapper.classRoomEntitiesToClassRoomResponse(classRoomRepository.findAllByBlocs(effectDate, hour, blocs));
	}

	@Override
	public List<RoomDTO> findRoomsWithoutDisponibilities() {
		return mapper.roomWithoutDisponibilityDTOsToRoomDTO(classRoomRepository.findRoomsWithoutDisponibilities());
	}

	@Override
	public void addDisponibility(String classRoomId, CreateDisponibilityRequest createDispoibilityRequest) {
		Optional<RoomEntity> roomEntityOptional = classRoomRepository.findById(classRoomId);
		if (roomEntityOptional.isPresent()) {
			DisponibilityEntity disponibilityEntity = mapper
					.createDispoibilityRequestToDisponibilityEntity(createDispoibilityRequest);
			disponibilityEntity.setRoom(roomEntityOptional.get());
			disponibilityRepository.save(disponibilityEntity);
		}
	}

	@Override
	public void updateDisponibility(CreateDisponibilityRequest createDispoibilityRequest) {
		Optional<DisponibilityEntity> disponibilityEntityOptional = disponibilityRepository
				.findById(createDispoibilityRequest.getDisponibilityId());
		if (disponibilityEntityOptional.isPresent()) {
			DisponibilityEntity disponibilityEntity = mapper
					.createDispoibilityRequestToDisponibilityEntity(createDispoibilityRequest);
			disponibilityRepository.save(disponibilityEntity);
		}
	}

	@Override
	public void deleteDisponibility(String disponibilityId) {
		Optional<DisponibilityEntity> disponibilityEntityOptional = disponibilityRepository.findById(disponibilityId);
		if (disponibilityEntityOptional.isPresent()) {
			disponibilityRepository.deleteById(disponibilityId);
		}
	}

	@Override
	public List<DisponibilityDTO> findDisponibilitiesByRoomId(String roomId) {
		return mapper.disponibilityEntitiesToDisponibilityEntities(disponibilityRepository.findAllByRoom(roomId));
	}

	@Override
	public List<RoomEntity> findRoomsByIds(List<String> rooms) {
		List<RoomEntity> roomEntities = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(rooms)) {
			rooms.forEach(roomId -> {
				Optional<RoomEntity> classRoomEntityOptional = classRoomRepository.findById(roomId);
				if (classRoomEntityOptional.isPresent()) {
					roomEntities.add(classRoomEntityOptional.get());
				}
			});
		}
		return roomEntities;
	}

}