package com.esprit.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.esprit.domain.ClassRoomEntity;
import com.esprit.domain.SiteEntity;
import com.esprit.dto.request.CreateClassRoomRequest;
import com.esprit.dto.response.ClassRoomResponse;
import com.esprit.repository.SiteRepository;

@Mapper(uses = DepartmentMapper.class)
public interface ClassRoomMapper {

	ClassRoomEntity createClassRoomRequestToClassRoomEntity(CreateClassRoomRequest createClassRoomRequest,
			@Context SiteRepository repository);

	ClassRoomResponse classRoomEntityToClassRoomResponse(ClassRoomEntity classRoomEntity);

	List<ClassRoomResponse> classRoomEntitiesToClassRoomResponse(List<ClassRoomEntity> classRoomEntities);

	@AfterMapping
	static void after(CreateClassRoomRequest source, @MappingTarget ClassRoomEntity target,
			@Context SiteRepository repository) {
		List<SiteEntity> siteEntities = new ArrayList<>();
		source.getSiteIds().forEach(id -> siteEntities.add(repository.getOne(id)));
		target.setSites(siteEntities);
	}

}