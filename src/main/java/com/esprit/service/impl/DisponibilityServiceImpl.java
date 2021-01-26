package com.esprit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esprit.domain.DisponibilityEntity;
import com.esprit.domain.RoomEntity;
import com.esprit.domain.TeacherEntity;
import com.esprit.dto.CreateDisponibilityRequest;
import com.esprit.dto.room.DisponibilityDTO;
import com.esprit.repository.DisponibilityRepository;
import com.esprit.repository.RoomRepository;
import com.esprit.repository.TeacherRepository;
import com.esprit.service.DisponibilityService;
import com.esprit.service.mapper.DisponibilityMapper;

@Service
public class DisponibilityServiceImpl implements DisponibilityService {

	private final DisponibilityRepository disponibilityRepository;

	private final TeacherRepository teacherRepository;

	private final RoomRepository roomRepository;

	private final DisponibilityMapper mapper;

	public DisponibilityServiceImpl(DisponibilityRepository disponibilityRepository,
			TeacherRepository teacherRepository, RoomRepository roomRepository, DisponibilityMapper mapper) {
		this.disponibilityRepository = disponibilityRepository;
		this.teacherRepository = teacherRepository;
		this.roomRepository = roomRepository;
		this.mapper = mapper;
	}

	@Override
	public void addDisponibilityToRoom(String roomId, CreateDisponibilityRequest createDisponibilityRequest) {
		Optional<RoomEntity> roomEntityOptional = roomRepository.findById(roomId);
		if (roomEntityOptional.isPresent()) {
			RoomEntity roomEntity = roomEntityOptional.get();
			roomEntity.addDisponibility(
					mapper.createDisponibilityRequestToDisponibilityEntity(createDisponibilityRequest));
			roomRepository.save(roomEntity);
		}
	}

	@Override
	public void addDisponibilityToTeacher(String teacherId, CreateDisponibilityRequest createDisponibilityRequest) {
		Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findById(teacherId);
		if (teacherEntityOptional.isPresent()) {
			TeacherEntity teacherEntity = teacherEntityOptional.get();
			teacherEntity.addDisponibility(
					mapper.createDisponibilityRequestToDisponibilityEntity(createDisponibilityRequest));
			teacherRepository.save(teacherEntity);
		}
	}

	@Override
	public void updateDisponibility(CreateDisponibilityRequest createDisponibilityRequest) {
		Optional<DisponibilityEntity> disponibilityEntityOptional = disponibilityRepository
				.findById(createDisponibilityRequest.getDisponibilityId());
		if (disponibilityEntityOptional.isPresent()) {
			DisponibilityEntity disponibilityEntityEntity = disponibilityEntityOptional.get();
			disponibilityRepository.save(disponibilityEntityEntity.startDate(createDisponibilityRequest.getStartDate())
					.endDate(createDisponibilityRequest.getEndDate()).motif(createDisponibilityRequest.getMotif())
					.startHour(createDisponibilityRequest.getStartHour())
					.endHour(createDisponibilityRequest.getEndHour()));
		}
	}

	@Override
	public void deleteDisponibilityFromTeacher(String teacherId, String disponibilityId) {
		Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findById(teacherId);
		Optional<DisponibilityEntity> disponibilityEntityOptional = disponibilityRepository.findById(disponibilityId);
		if (teacherEntityOptional.isPresent() && disponibilityEntityOptional.isPresent()) {
			TeacherEntity teacherEntity = teacherEntityOptional.get();
			teacherEntity.removeDisponibility(disponibilityEntityOptional.get());
			teacherRepository.save(teacherEntity);
			disponibilityRepository.delete(disponibilityEntityOptional.get());
		}
	}

	@Override
	public void deleteDisponibilityFromRoom(String roomId, String disponibilityId) {
		Optional<RoomEntity> roomEntityOptional = roomRepository.findById(roomId);
		Optional<DisponibilityEntity> disponibilityEntityOptional = disponibilityRepository.findById(disponibilityId);
		if (roomEntityOptional.isPresent() && disponibilityEntityOptional.isPresent()) {
			RoomEntity roomEntity = roomEntityOptional.get();
			roomEntity.removeDisponibility(disponibilityEntityOptional.get());
			roomRepository.save(roomEntity);
			disponibilityRepository.delete(disponibilityEntityOptional.get());
		}
	}

	@Override
	public List<DisponibilityDTO> findAllByTeacher(String teacherId) {
		return mapper.disponibilityEntitiesToDisponibilityEntities(disponibilityRepository.findAllByTeacher(teacherId));
	}

	@Override
	public List<DisponibilityDTO> findAllByRoom(String roomId) {
		return mapper.disponibilityEntitiesToDisponibilityEntities(disponibilityRepository.findAllByRoom(roomId));
	}

}