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

import com.esprit.domain.TeacherEntity;
import com.esprit.dto.teacher.CreateTeacherRequest;
import com.esprit.dto.teacher.TeacherDTO;
import com.esprit.error.exception.EntityNotFoundException;
import com.esprit.repository.TeacherRepository;
import com.esprit.service.TeacherService;
import com.esprit.service.mapper.TeacherMapper;

@Service
public class TeacherServiceImpl implements TeacherService {

	private final TeacherRepository teacherRepository;

	private final TeacherMapper mapper;

	public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper mapper) {
		this.teacherRepository = teacherRepository;
		this.mapper = mapper;
	}

	@Override
	public void addTeacher(CreateTeacherRequest createTeacherRequest) {
		if (!StringUtils.isBlank(createTeacherRequest.getTeacherId())) {
			TeacherEntity teacherEntity = mapper.createTeacherRequestToTeacherEntity(createTeacherRequest);
			if (CollectionUtils.isNotEmpty(teacherEntity.getDisponibilities())) {
				Map<String, Integer> namesMap = new HashMap<>(6);
				namesMap.put("lundi", 0);
				namesMap.put("mardi", 1);
				namesMap.put("mercredi", 2);
				namesMap.put("jeudi", 3);
				namesMap.put("vendredi", 4);
				namesMap.put("samedi", 5);
				teacherEntity.getDisponibilities().forEach(disp -> {
					if (disp.getWeek() != null && !StringUtils.isBlank(disp.getDay())) {
						disp.getDay().toUpperCase();
						Calendar c = Calendar.getInstance();
						c.setTime(disp.getWeek().getStartDate());
						c.add(Calendar.DATE, namesMap.get(disp.getDay()));
						disp.setExactDate(c.getTime());
					}
				});
				teacherEntity.addDisponibilities(teacherEntity.getDisponibilities());
			}
			teacherEntity = teacherRepository.save(teacherEntity);
		}
	}

	@Override
	public TeacherDTO findBydId(String userId) {
		TeacherDTO result = null;
		if (!StringUtils.isBlank(userId)) {
			Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findById(userId);
			if (teacherEntityOptional.isPresent()) {
				result = mapper.teacherEntityToTeacherDTO(teacherEntityOptional.get());
			} else {
				throw new EntityNotFoundException(TeacherDTO.class, "id", userId);
			}
		}
		return result;
	}

	@Override
	public List<TeacherDTO> findTeachers() {
		return mapper.teacherEntitiesToTeacherDTO(teacherRepository.findAll());
	}

	@Override
	public List<TeacherDTO> searcheachers(String teacherId, int cin, String departement, String classs) {
		return mapper.teacherEntitiesToTeacherDTO(teacherRepository.searcheachers(teacherId, cin, departement, classs));
	}

	@Override
	public List<TeacherDTO> findAllByUps(List<String> ups) {
		return mapper.teacherEntitiesToTeacherDTO(teacherRepository.findAllByUps(ups));
	}

	@Override
	public List<TeacherEntity> findTeachersByIds(List<String> teachers) {
		List<TeacherEntity> teacherEntities = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(teachers)) {
			teachers.forEach(teacherId -> {
				Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findById(teacherId);
				if (teacherEntityOptional.isPresent()) {
					teacherEntities.add(teacherEntityOptional.get());
				}
			});
		}
		return teacherEntities;
	}

	@Override
	public void addDisponibilityToTeacher(String teacherId, Date supervisionDate, int supervisionHour,
			int supervisionDuration) {
		/*Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findById(teacherId);
		if (teacherEntityOptional.isPresent()) {
			TeacherEntity teacherEntity = teacherEntityOptional.get();
			DisponibilityTeacherEntity disponibilityTeacher = new DisponibilityTeacherEntity()
					.supervisionDate(supervisionDate).supervisionHour(supervisionHour)
					.supervisionDuration(supervisionDuration);
			teacherEntity.addDisponibility(disponibilityTeacher);
			teacherRepository.save(teacherEntity);
		}*/
	}

}