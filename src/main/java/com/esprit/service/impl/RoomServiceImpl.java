package com.esprit.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.esprit.domain.PeriodEntity;
import com.esprit.domain.RoomEntity;
import com.esprit.domain.SeanceEntity;
import com.esprit.domain.SemesterEntity;
import com.esprit.domain.WeekEntity;
import com.esprit.dto.CreateDisponibilityRequest;
import com.esprit.dto.room.CreateRoomRequest;
import com.esprit.dto.room.FilterAvailableRoomDTO;
import com.esprit.dto.room.RoomDTO;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.PeriodRepository;
import com.esprit.repository.RoomRepository;
import com.esprit.repository.SeanceRepository;
import com.esprit.repository.SemesterRepository;
import com.esprit.repository.WeekRepository;
import com.esprit.service.RoomService;
import com.esprit.service.mapper.RoomMapper;

@Service
public class RoomServiceImpl implements RoomService {

	private final RoomRepository classRoomRepository;

	private final WeekRepository weekRepository;

	private final PeriodRepository periodRepository;

	private final SemesterRepository semesterRepository;

	private final SeanceRepository seanceRepository;

	private final RoomMapper mapper;

	public RoomServiceImpl(RoomRepository classRoomRepository, WeekRepository weekRepository,
			PeriodRepository periodRepository, SemesterRepository semesterRepository, SeanceRepository seanceRepository,
			RoomMapper mapper) {
		this.classRoomRepository = classRoomRepository;
		this.weekRepository = weekRepository;
		this.periodRepository = periodRepository;
		this.semesterRepository = semesterRepository;
		this.seanceRepository = seanceRepository;
		this.mapper = mapper;
	}

	@Override
	public void addClassRoom(CreateRoomRequest createRoomRequest) {
		if (CollectionUtils.isNotEmpty(createRoomRequest.getDisponibilities())) {
			Map<String, Integer> namesMap = new HashMap<>(6);
			namesMap.put("lundi", 0);
			namesMap.put("mardi", 1);
			namesMap.put("mercredi", 2);
			namesMap.put("jeudi", 3);
			namesMap.put("vendredi", 4);
			namesMap.put("samedi", 5);
			for (CreateDisponibilityRequest disp : createRoomRequest.getDisponibilities()) {
				if (!StringUtils.isBlank(disp.getWeekId()) && !StringUtils.isBlank(disp.getDay())) {
					Optional<WeekEntity> weekOptional = weekRepository.findById(disp.getWeekId());
					if (weekOptional.isPresent()) {
						disp.getDay().toUpperCase();
						Calendar c = Calendar.getInstance();
						c.setTime(weekOptional.get().getStartDate());
						c.add(Calendar.DATE, namesMap.get(disp.getDay()));
						disp.setStartDate(c.getTime());
						disp.setEndDate(c.getTime());
					}
				} else if (!StringUtils.isBlank(disp.getWeekId()) && StringUtils.isBlank(disp.getDay())) {
					Optional<WeekEntity> weekOptional = weekRepository.findById(disp.getWeekId());
					disp.setStartDate(weekOptional.get().getStartDate());
					disp.setEndDate(weekOptional.get().getEndDate());
				} else if (!StringUtils.isBlank(disp.getPeriodId()) && StringUtils.isBlank(disp.getWeekId())
						&& StringUtils.isBlank(disp.getDay())) {
					Optional<PeriodEntity> periodOptional = periodRepository.findById(disp.getWeekId());
					disp.setStartDate(periodOptional.get().getStartDate());
					disp.setEndDate(periodOptional.get().getEndDate());
				} else if (!StringUtils.isBlank(disp.getSemesterId()) && StringUtils.isBlank(disp.getPeriodId())
						&& StringUtils.isBlank(disp.getWeekId()) && StringUtils.isBlank(disp.getDay())) {
					Optional<SemesterEntity> semesterOptional = semesterRepository.findById(disp.getWeekId());
					disp.setStartDate(semesterOptional.get().getStartDate());
					disp.setEndDate(semesterOptional.get().getEndDate());
				}

				if (!StringUtils.isBlank(disp.getSeanceId())) {
					Optional<SeanceEntity> seanceOptional = seanceRepository.findById(disp.getSeanceId());
					if (seanceOptional.isPresent()) {
						disp.setStartHour(seanceOptional.get().getStartHour());
						disp.setEndHour(seanceOptional.get().getEndHour());
					}
				}
			}
		}
		RoomEntity classRoomEntity = mapper.createRoomRequestToClassRoomEntity(createRoomRequest);
		if (CollectionUtils.isNotEmpty(classRoomEntity.getDisponibilities())) {
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
	public List<RoomDTO> findAllByBlocs(FilterAvailableRoomDTO filterAvailableRoomDTO) {
		List<RoomEntity> roomEntities = classRoomRepository.findAllByBlocs(filterAvailableRoomDTO.getEffectDate(),
				filterAvailableRoomDTO.getHour(), filterAvailableRoomDTO.getBlocs());
		return mapper.classRoomEntitiesToClassRoomResponse(
				roomEntities.stream().filter(distinctByKey(RoomEntity::getClassRoomId)).collect(Collectors.toList()));
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
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

	@Override
	public List<String> findBlocByRooms(List<String> rooms) {
		return classRoomRepository.findBlocByRooms(rooms);
	}

}