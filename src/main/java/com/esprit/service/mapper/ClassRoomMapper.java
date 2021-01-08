package com.esprit.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.ClassRoomEntity;
import com.esprit.domain.SiteEntity;
import com.esprit.dto.request.CreateClassRoomRequest;
import com.esprit.dto.request.UpdateClassRoomRequest;
import com.esprit.dto.response.ClassRoomResponse;
import com.esprit.repository.SiteRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClassRoomMapper {

	ClassRoomMapper INSTANCE = Mappers.getMapper(ClassRoomMapper.class);

	ClassRoomEntity createClassRoomRequestToClassRoomEntity(CreateClassRoomRequest createClassRoomRequest,
			@Context SiteRepository repository);

	ClassRoomEntity updateClassRoomRequestToClassRoomEntity(UpdateClassRoomRequest updateClassRoomRequest,
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

	@AfterMapping
	static void after(UpdateClassRoomRequest source, @MappingTarget ClassRoomEntity target,
			@Context SiteRepository repository) {
		List<SiteEntity> siteEntities = new ArrayList<>();
		source.getSiteIds().forEach(id -> siteEntities.add(repository.getOne(id)));
		target.setSites(siteEntities);
	}

}