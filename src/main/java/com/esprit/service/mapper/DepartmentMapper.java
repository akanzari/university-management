package com.esprit.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.DepartmentEntity;
import com.esprit.domain.SiteEntity;
import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.request.CreateDepartmentRequest;
import com.esprit.dto.response.DepartmentResponse;
import com.esprit.repository.SiteRepository;
import com.esprit.repository.SpecialityRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {

	DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

	DepartmentEntity createDepartmentRequestToDepartmentEntity(CreateDepartmentRequest createDepartmentRoomRequest,
			@Context SiteRepository siteRepository, @Context SpecialityRepository specialityRepository);

	DepartmentResponse departmentEntityToDepartmentResponse(DepartmentEntity departmentEntity);

	List<DepartmentResponse> departmentEntitiesToDepartmentResponse(List<DepartmentEntity> departmentEntities);

	@AfterMapping
	static void after(CreateDepartmentRequest source, @MappingTarget DepartmentEntity target,
			@Context SiteRepository siteRepository, @Context SpecialityRepository specialityRepository) {
		List<SiteEntity> siteEntities = new ArrayList<>();
		source.getSiteIds().forEach(id -> siteEntities.add(siteRepository.getOne(id)));
		target.setSites(siteEntities);
		List<SpecialityEntity> specialityEntities = new ArrayList<>();
		source.getSpecialityIds().forEach(id -> specialityEntities.add(specialityRepository.getOne(id)));
		target.setSpecialities(specialityEntities);
	}

}