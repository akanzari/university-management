package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.DepartmentEntity;
import com.esprit.dto.DepartmentDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmenetMapper {

	DepartmenetMapper INSTANCE = Mappers.getMapper(DepartmenetMapper.class);

	DepartmentEntity departmentDTOToDepartmentEntity(DepartmentDTO departmentDTO);

	DepartmentDTO departmentEntityToDepartementDTO(DepartmentEntity departmentEntity);

	List<DepartmentDTO> departmentEntitiesToDepartementDTO(List<DepartmentEntity> departmentEntities);

}