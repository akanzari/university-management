package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.ClassRoomEntity;
import com.esprit.dto.request.rooms.CreateClassRoomRequest;
import com.esprit.dto.request.rooms.UpdateClassRoomRequest;
import com.esprit.dto.response.ClassRoomResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClassRoomMapper {

	ClassRoomMapper INSTANCE = Mappers.getMapper(ClassRoomMapper.class);

	ClassRoomEntity createClassRoomRequestToClassRoomEntity(CreateClassRoomRequest createClassRoomRequest);

	ClassRoomEntity updateClassRoomRequestToClassRoomEntity(UpdateClassRoomRequest updateClassRoomRequest);

	ClassRoomResponse classRoomEntityToClassRoomResponse(ClassRoomEntity classRoomEntity);

	List<ClassRoomResponse> classRoomEntitiesToClassRoomResponse(List<ClassRoomEntity> classRoomEntities);

}