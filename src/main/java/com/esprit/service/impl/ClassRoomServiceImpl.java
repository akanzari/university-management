package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.ClassRoomEntity;
import com.esprit.domain.SiteEntity;
import com.esprit.dto.request.CreateClassRoomRequest;
import com.esprit.dto.request.UpdateClassRoomRequest;
import com.esprit.dto.response.ClassRoomResponse;
import com.esprit.error.exception.EntityAlreadyExistsExeption;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.ClassRoomRepository;
import com.esprit.repository.SiteRepository;
import com.esprit.service.ClassRoomService;
import com.esprit.service.mapper.ClassRoomMapper;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {

	private final ClassRoomRepository classRoomRepository;

	private final SiteRepository siteRepository;

	private final ClassRoomMapper mapper;

	public ClassRoomServiceImpl(ClassRoomRepository classRoomRepository, SiteRepository siteRepository,
			ClassRoomMapper mapper) {
		this.classRoomRepository = classRoomRepository;
		this.siteRepository = siteRepository;
		this.mapper = mapper;
	}

	@Override
	public ClassRoomResponse addClassRoom(CreateClassRoomRequest createClassRoomRequest) {
		ClassRoomEntity classRoomEntity = mapper.createClassRoomRequestToClassRoomEntity(createClassRoomRequest,
				siteRepository);

		if (classRoomRepository.findByCode(classRoomEntity.getCode()) != null) {
			throw new EntityAlreadyExistsExeption(ClassRoomEntity.class, "code", classRoomEntity.getCode());
		}

		createClassRoomRequest.getSiteIds().forEach(id -> {
			if (!siteRepository.findById(id).isPresent()) {
				throw new EntityNotFoundException(SiteEntity.class, "id", id);
			}
		});

		classRoomEntity = classRoomRepository.save(classRoomEntity);
		return mapper.classRoomEntityToClassRoomResponse(classRoomEntity);
	}

	@Override
	public ClassRoomResponse updateClassRoom(UpdateClassRoomRequest updateClassRoomRequest) {
		ClassRoomEntity classRoomEntity;
		Optional<ClassRoomEntity> classRoomEntityOptional = classRoomRepository
				.findById(updateClassRoomRequest.getClassRoomId());
		if (classRoomEntityOptional.isPresent()) {
			classRoomEntity = mapper.updateClassRoomRequestToClassRoomEntity(updateClassRoomRequest, siteRepository);
			updateClassRoomRequest.getSiteIds().forEach(id -> {
				if (siteRepository.getOne(id) == null) {
					throw new EntityNotFoundException(SiteEntity.class, "id", id);
				}
			});
			classRoomEntity.setCode(classRoomEntityOptional.get().getCode());
			classRoomEntity = classRoomRepository.save(classRoomEntity);
		} else {
			throw new EntityNotFoundException(ClassRoomEntity.class, "Id", updateClassRoomRequest.getClassRoomId());
		}
		return mapper.classRoomEntityToClassRoomResponse(classRoomEntity);
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