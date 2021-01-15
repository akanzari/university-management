package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.RoomEntity;
import com.esprit.dto.RoomDTO;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.RoomRepository;
import com.esprit.service.RoomService;
import com.esprit.service.mapper.RoomMapper;

@Service
public class RoomServiceImpl implements RoomService {

	private final RoomRepository classRoomRepository;

	private final RoomMapper mapper;

	public RoomServiceImpl(RoomRepository classRoomRepository, RoomMapper mapper) {
		this.classRoomRepository = classRoomRepository;
		this.mapper = mapper;
	}

	@Override
	public void addClassRoom(RoomDTO roomDTO) {
		RoomEntity classRoomEntity = mapper.createClassRoomRequestToClassRoomEntity(roomDTO);
		classRoomRepository.save(classRoomEntity);
	}

	@Override
	public void updateClassRoom(RoomDTO roomDTO) {
		RoomEntity classRoomEntity;
		Optional<RoomEntity> classRoomEntityOptional = classRoomRepository.findById(roomDTO.getClassRoomId());
		if (classRoomEntityOptional.isPresent()) {
			classRoomEntity = mapper.updateClassRoomRequestToClassRoomEntity(roomDTO);
			classRoomRepository.save(classRoomEntity);
		} else {
			throw new EntityNotFoundException(RoomEntity.class, "Id", roomDTO.getClassRoomId());
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
	public List<RoomDTO> findClassRooms() {
		return mapper.classRoomEntitiesToClassRoomResponse(classRoomRepository.findAll());
	}

}