package com.esprit.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.esprit.domain.AssignClassModuleEntity;
import com.esprit.domain.ClassEntity;
import com.esprit.domain.DisponibilityEntity;
import com.esprit.domain.ExamEntity;
import com.esprit.domain.RoomEntity;
import com.esprit.domain.TeacherEntity;
import com.esprit.dto.classes.ClassDTO;
import com.esprit.dto.exam.CreateExamRequest;
import com.esprit.dto.exam.DeleteExamDTO;
import com.esprit.dto.exam.ExamDTO;
import com.esprit.dto.module.ModuleDTO;
import com.esprit.dto.room.RoomDTO;
import com.esprit.dto.teacher.TeacherDTO;
import com.esprit.error.exception.ModuleExistInSessionException;
import com.esprit.error.exception.RoomSizeExecption;
import com.esprit.error.exception.TeacherSizeExecption;
import com.esprit.repository.AssignClassModuleRepository;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.DisponibilityRepository;
import com.esprit.repository.ExamRepository;
import com.esprit.repository.ModuleRepository;
import com.esprit.repository.RoomRepository;
import com.esprit.repository.TeacherRepository;
import com.esprit.service.ExamService;
import com.esprit.service.mapper.ExamMapper;

@Service
public class ExamServiceImpl implements ExamService {

	private final ExamRepository examRepository;

	private final RoomRepository roomRepository;

	private final ClassRepository classRepository;

	private final ModuleRepository moduleRepository;

	private final TeacherRepository teacherRepository;

	private final DisponibilityRepository disponibilityRepository;

	private final AssignClassModuleRepository assignClassModuleRepository;

	private final ExamMapper mapper;

	public ExamServiceImpl(ExamRepository examRepository, RoomRepository roomRepository,
			ClassRepository classRepository, ModuleRepository moduleRepository, TeacherRepository teacherRepository,
			DisponibilityRepository disponibilityRepository, AssignClassModuleRepository assignClassModuleRepository,
			ExamMapper mapper) {
		this.examRepository = examRepository;
		this.roomRepository = roomRepository;
		this.classRepository = classRepository;
		this.moduleRepository = moduleRepository;
		this.teacherRepository = teacherRepository;
		this.disponibilityRepository = disponibilityRepository;
		this.assignClassModuleRepository = assignClassModuleRepository;
		this.mapper = mapper;
	}

	@Override
	public void addExam(CreateExamRequest createExamRequest) {
		ExamEntity examEntityBySession = examRepository.findBySession(createExamRequest.getSession());
		if (examEntityBySession != null) {
			boolean isModuleExistInSession = examEntityBySession.getClasses().stream()
					.anyMatch(item -> item.getModule().getModuleId().equals(createExamRequest.getModule()));
			if (isModuleExistInSession) {
				throw new ModuleExistInSessionException("Module exist in this session");
			}
		}
		ExamEntity examEntity = mapper.createExamRequestToExamEntity(createExamRequest);
		List<ClassEntity> classEntities = mapClass(createExamRequest);
		examEntity.setClasses(classEntities);
		examRepository.save(examEntity);
	}

	private List<ClassEntity> mapClass(CreateExamRequest createExamRequest) {
		List<ClassEntity> classEntities = new ArrayList<>();
		for (String cl : createExamRequest.getClasses()) {
			Optional<ClassEntity> classEntityOptional = classRepository.findById(cl);
			if (classEntityOptional.isPresent()) {

				ClassEntity classEntity = classEntityOptional.get();
				classEntity.examDate(createExamRequest.getExamDate()).examDuration(createExamRequest.getExamDuration())
						.examHour(createExamRequest.getExamHour())
						.module(moduleRepository.getOne(createExamRequest.getModule()));

				if (CollectionUtils.isEmpty(createExamRequest.getRooms())) {
					List<RoomEntity> roomEntities = roomRepository.findAllByBlocs(createExamRequest.getExamDate(),
							createExamRequest.getExamHour(), createExamRequest.getBloc());
					if (roomEntities.size() < createExamRequest.getClasses().size() * 2) {
						throw new RoomSizeExecption("");
					} else {
						List<RoomEntity> currentRooms = getRandomRoomEntity(roomEntities, 2);
						classEntity.rooms(currentRooms);
					}
				} else {
					if (createExamRequest.getRooms().size() < createExamRequest.getClasses().size() * 2) {
						throw new RoomSizeExecption("");
					} else {
						List<RoomEntity> roomEntitys = roomRepository.findByClassRoomIdIn(createExamRequest.getRooms());
						classEntity.rooms(roomEntitys);
					}
				}

				if (CollectionUtils.isEmpty(createExamRequest.getTeachers())) {
					List<TeacherEntity> teacherEntities = teacherRepository.findAllByUps(
							createExamRequest.getExamDate(), createExamRequest.getExamHour(),
							createExamRequest.getUp());
					if (teacherEntities.size() < createExamRequest.getClasses().size() * 2) {
						throw new TeacherSizeExecption("");
					} else {
						List<TeacherEntity> currentTeacher = getRandomTeacherEntity(teacherEntities, 2);
						classEntity.teachers(currentTeacher);
					}
				} else {
					if (createExamRequest.getTeachers().size() < createExamRequest.getClasses().size() * 2) {
						throw new TeacherSizeExecption("");
					} else {
						List<TeacherEntity> teacherEntities = teacherRepository
								.findByTeacherIdIn(createExamRequest.getTeachers());
						classEntity.teachers(teacherEntities);
					}
				}

				classEntities.add(classEntity);

				if (CollectionUtils.isNotEmpty(classEntity.getTeachers())) {
					for (TeacherEntity teacher : classEntity.getTeachers()) {
						int duration = createExamRequest.getExamHour()
								+ Integer.parseInt((createExamRequest.getExamDuration() / 60) + ""
										+ (createExamRequest.getExamDuration() % 60));
						teacher.addDisponibility(new DisponibilityEntity().startDate(createExamRequest.getExamDate())
								.endDate(createExamRequest.getExamDate()).startHour(createExamRequest.getExamHour())
								.endHour(duration).motif("Surveiller"));
						teacherRepository.save(teacher);
					}
				}

				if (CollectionUtils.isNotEmpty(classEntity.getRooms())) {
					for (RoomEntity room : classEntity.getRooms()) {
						String motif = null;
						if (createExamRequest.getSession().equalsIgnoreCase("S1P1")
								|| createExamRequest.getSession().equalsIgnoreCase("S2P1")) {
							motif = "DS";
						} else if (createExamRequest.getSession().equalsIgnoreCase("S1P2")
								|| createExamRequest.getSession().equalsIgnoreCase("S2P2")
								|| createExamRequest.getSession().equalsIgnoreCase("SP")) {
							motif = "Examen";
						} else if (createExamRequest.getSession().equalsIgnoreCase("SR")) {
							motif = "Rattrapage";
						}
						int duration = createExamRequest.getExamHour()
								+ Integer.parseInt((createExamRequest.getExamDuration() / 60) + ""
										+ (createExamRequest.getExamDuration() % 60));
						room.addDisponibility(new DisponibilityEntity().startDate(createExamRequest.getExamDate())
								.endDate(createExamRequest.getExamDate()).startHour(createExamRequest.getExamHour())
								.endHour(duration).motif(motif));
						roomRepository.save(room);
					}
				}
			}
		}
		return classEntities;
	}

	public List<RoomEntity> getRandomRoomEntity(List<RoomEntity> list, int totalItems) {
		Random rand = new Random();
		List<RoomEntity> newList = new ArrayList<>();
		for (int i = 0; i < totalItems; i++) {
			int randomIndex = rand.nextInt(list.size());
			newList.add(list.get(randomIndex));
			list.remove(randomIndex);
		}
		return newList;
	}

	public List<TeacherEntity> getRandomTeacherEntity(List<TeacherEntity> list, int totalItems) {
		Random rand = new Random();
		List<TeacherEntity> newList = new ArrayList<>();
		for (int i = 0; i < totalItems; i++) {
			int randomIndex = rand.nextInt(list.size());
			newList.add(list.get(randomIndex));
			list.remove(randomIndex);
		}
		return newList;
	}

	@Override
	public void updateExam(CreateExamRequest updateExamRequest) {
		Optional<ExamEntity> examEntityOptional = examRepository.findById(updateExamRequest.getExamId());
		if (examEntityOptional.isPresent()) {
			ExamEntity examEntity = examEntityOptional.get();
			List<ClassEntity> classEntities = mapClass(updateExamRequest);
			examEntity.setClasses(classEntities);
			examRepository.save(examEntity);

		}
	}

	@Override
	public void deleteExam(DeleteExamDTO deleteExamDTO) {
		Optional<ExamEntity> examEntityOptional = examRepository.findById(deleteExamDTO.getExamId());
		if (examEntityOptional.isPresent()) {
			ExamEntity exam = examEntityOptional.get();
			ClassEntity classEntity = classRepository.getOne(deleteExamDTO.getClasse());
			List<TeacherEntity> teacherEntities = teacherRepository.findByTeacherIdIn(deleteExamDTO.getTeacherIds());
			if (CollectionUtils.isNotEmpty(teacherEntities)) {
				for (TeacherEntity teacher : teacherEntities) {
					List<DisponibilityEntity> disp = teacher.getDisponibilities().stream()
							.filter(item -> item.getStartDate().equals(classEntity.getExamDate())
									&& item.getStartHour() == classEntity.getExamHour())
							.collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(disp)) {
						teacher.removeDisponibilities(disp);
						teacherRepository.save(teacher);
						disponibilityRepository.deleteAll(disp);
					}
				}
			}
			List<RoomEntity> roomEntities = roomRepository.findByClassRoomIdIn(deleteExamDTO.getRoomIds());
			if (CollectionUtils.isNotEmpty(roomEntities)) {
				for (RoomEntity room : roomEntities) {
					List<DisponibilityEntity> disp = room.getDisponibilities().stream()
							.filter(item -> item.getStartDate().equals(classEntity.getExamDate())
									&& item.getStartHour() == classEntity.getExamHour())
							.collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(disp)) {
						room.removeDisponibilities(disp);
						roomRepository.save(room);
						disponibilityRepository.deleteAll(disp);
					}
				}
			}
			examRepository.delete(exam);
		}
	}

	@Override
	public List<ExamDTO> findExams() {
		return examRepository.findAll().stream().map(this::examEntityToExamDTO)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	private ExamDTO examEntityToExamDTO(ExamEntity examEntity) {
		ExamDTO result = new ExamDTO().examId(examEntity.getExamId()).session(examEntity.getSession());
		List<ClassDTO> classDTOs = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(examEntity.getClasses())) {
			for (ClassEntity cl : examEntity.getClasses()) {
				List<RoomDTO> rooms = cl.getRooms().stream()
						.map(room -> new RoomDTO().classRoomId(room.getClassRoomId())).collect(Collectors.toList());
				List<TeacherDTO> teachers = cl.getTeachers().stream().map(
						teacher -> new TeacherDTO().teacherId(teacher.getTeacherId()).fullName(teacher.getFullName()))
						.collect(Collectors.toList());

				ClassDTO classDTO = new ClassDTO().classId(cl.getClassId()).label(cl.getLabel())
						.category(cl.getCategory()).examDate(cl.getExamDate()).examHour(cl.getExamHour())
						.examDuration(cl.getExamDuration()).module(new ModuleDTO()
								.moduleId(cl.getModule().getModuleId()).designation(cl.getModule().getDesignation()))
						.teachers(teachers).rooms(rooms);
				AssignClassModuleEntity assignClassModuleEntity = assignClassModuleRepository
						.findByClass(cl.getClassId());
				if (assignClassModuleEntity != null && !StringUtils.isBlank(assignClassModuleEntity.getTypeExam())) {
					classDTO.typeExam(assignClassModuleEntity.getTypeExam());
				}
				classDTOs.add(classDTO);
			}
		}
		result.classes(classDTOs);
		return result;
	}

}