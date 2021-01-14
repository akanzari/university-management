package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.StudentEntity;
import com.esprit.domain.TeacherEntity;
import com.esprit.dto.TeacherDTO;
import com.esprit.dto.request.CreateStudentRequest;
import com.esprit.dto.response.StudentResponse;
import com.esprit.dto.response.UserResponse;
import com.esprit.repository.ClassRepository;
import com.esprit.service.IAMService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "fullName", qualifiedByName = "IAMService, findFullNameById")
	StudentEntity createStudentRequestToStudentEntity(CreateStudentRequest createStudentRequest,
			@Context ClassRepository classRepository);

	StudentResponse studentEntityToStudentResponse(StudentEntity studentEntity);

	TeacherEntity teacherDTOtoTeacherEntity(TeacherDTO teacherDTO);

	TeacherDTO teacherEntityToTeacherDTO(TeacherEntity teacherEntity, @Context IAMService iamService);

	List<TeacherDTO> teacherEntitiesToTeacherDTO(List<TeacherEntity> teacherEntities, @Context IAMService iamService);

	@AfterMapping
	static void after(CreateStudentRequest source, @MappingTarget StudentEntity target,
			@Context ClassRepository classRepository) {
		target.setClasss(classRepository.getOne(source.getClassId()));
	}

	@AfterMapping
	static void after(TeacherEntity source, @MappingTarget TeacherDTO target, @Context IAMService iamService) {
		UserResponse user = iamService.findUser(source.getTeacherId());
		if (user != null) {
			target.setFullName(user.getFirstName());
			target.setEmail(user.getEmail());
			target.setDepartement(user.getDepatement());
		}
	}

}