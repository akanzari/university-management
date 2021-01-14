package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.ClassRoomEntity;
import com.esprit.dto.request.rooms.CreateClassRoomRequest;
import com.esprit.dto.request.rooms.UpdateClassRoomRequest;
import com.esprit.dto.response.ClassRoomResponse;
import com.esprit.error.exception.EntityAlreadyExistsExeption;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.ClassRoomRepository;
import com.esprit.service.ClassRoomService;
import com.esprit.service.mapper.ClassRoomMapper;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {

	private final ClassRoomRepository classRoomRepository;

	private final ClassRoomMapper mapper;

	public ClassRoomServiceImpl(ClassRoomRepository classRoomRepository, ClassRoomMapper mapper) {
		this.classRoomRepository = classRoomRepository;
		this.mapper = mapper;
	}

	@Override
	public void addClassRoom(CreateClassRoomRequest createClassRoomRequest) {
		ClassRoomEntity classRoomEntity = mapper.createClassRoomRequestToClassRoomEntity(createClassRoomRequest);
		if (classRoomRepository.findByLabel(classRoomEntity.getLabel()) != null) {
			throw new EntityAlreadyExistsExeption(ClassRoomEntity.class, "label", classRoomEntity.getLabel());
		}
		classRoomRepository.save(classRoomEntity);
	}

	@Override
	public void updateClassRoom(UpdateClassRoomRequest updateClassRoomRequest) {
		ClassRoomEntity classRoomEntity;
		Optional<ClassRoomEntity> classRoomEntityOptional = classRoomRepository
				.findById(updateClassRoomRequest.getClassRoomId());
		if (classRoomEntityOptional.isPresent()) {
			classRoomEntity = mapper.updateClassRoomRequestToClassRoomEntity(updateClassRoomRequest);
			classRoomRepository.save(classRoomEntity);
		} else {
			throw new EntityNotFoundException(ClassRoomEntity.class, "Id", updateClassRoomRequest.getClassRoomId());
		}
	}

	@Override
	public void deleteClassRoom(String classRoomId) {
		if (findClassRoom(classRoomId) != null) {
			classRoomRepository.deleteById(classRoomId);
		}
	}

	@Override
	public ClassRoomResponse findClassRoom(String classRoomId) {
		ClassRoomResponse result = null;
		Optional<ClassRoomEntity> classRoomEntityOptional = classRoomRepository.findById(classRoomId);
		if (classRoomEntityOptional.isPresent()) {
			result = mapper.classRoomEntityToClassRoomResponse(classRoomEntityOptional.get());
		} else {
			throw new EntityNotFoundException(ClassRoomEntity.class, "id", classRoomId);
		}
		return result;
	}

	@Override
	public List<ClassRoomResponse> findClassRooms() {
		return mapper.classRoomEntitiesToClassRoomResponse(classRoomRepository.findAll());
	}

}