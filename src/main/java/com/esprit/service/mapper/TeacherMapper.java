package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.esprit.domain.TeacherEntity;
import com.esprit.dto.TeacherDTO;
import com.esprit.dto.response.UserResponse;
import com.esprit.service.IAMService;

@Mapper
public interface TeacherMapper {

	TeacherEntity teacherDTOtoTeacherEntity(TeacherDTO teacherDTO);

	TeacherDTO teacherEntityToTeacherDTO(TeacherEntity teacherEntity, @Context IAMService iamService);

	List<TeacherDTO> teacherEntitiesToTeacherDTO(List<TeacherEntity> teacherEntities, @Context IAMService iamService);

	@AfterMapping
	static void after(TeacherEntity source, @MappingTarget TeacherDTO target, @Context IAMService iamService) {
		UserResponse user = iamService.findUser(source.getTeacherId());
		if (user != null) {
			target.setFullName(user.getFirstName());
			target.setEmail(user.getEmail());
		}
	}

}