package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.AssignClassModuleEntity;
import com.esprit.dto.module.AssignClassModuleDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { SemesterMapper.class, PeriodMapper.class })
public interface AssignClassModuleMapper {

	AssignClassModuleMapper INSTANCE = Mappers.getMapper(AssignClassModuleMapper.class);

	@Mapping(target = "classs.module", ignore = true)
	@Mapping(target = "classs.teachers", ignore = true)
	@Mapping(target = "classs.rooms", ignore = true)
	AssignClassModuleDTO assignClassModuleEntityAssignClassModuleDTO(AssignClassModuleEntity assignClassModuleEntity);

	List<AssignClassModuleDTO> assignClassModuleAssignClassModuleDTO(
			List<AssignClassModuleEntity> assignClassModuleEntities);

}