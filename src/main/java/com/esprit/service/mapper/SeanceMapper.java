package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.SeanceEntity;
import com.esprit.dto.seance.CreateSeanceRequest;
import com.esprit.dto.seance.SeanceDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeanceMapper {

	SeanceMapper INSTANCE = Mappers.getMapper(SeanceMapper.class);

	SeanceEntity createSeanceRequestToSeanceEntity(CreateSeanceRequest createSeanceRequest);

	@Mapping(target = "startHour", qualifiedByName = "toStartHour")
	@Mapping(source = "endHour", target = "endHour", qualifiedByName = "toEndHour")
	SeanceDTO seanceEntityToseanceDTO(SeanceEntity seanceEntity);

	List<SeanceDTO> SeanceEntitiesToseanceDTO(List<SeanceEntity> SeanceEntities);

	@Named("toStartHour")
	default String toStartHour(int startHour) {
		String currentStartHour = Integer.toString(startHour);
		if (currentStartHour.length() == 3) {
			currentStartHour = "0" + currentStartHour;
		}
		return currentStartHour.substring(0, 2) + "H:" + currentStartHour.substring(2, currentStartHour.length());
	}

	@Named("toEndHour")
	default String toEndHour(int endHour) {
		String currentEndHour = Integer.toString(endHour);
		if (currentEndHour.length() == 3) {
			currentEndHour = "0" + currentEndHour;
		}
		return currentEndHour.substring(0, 2) + "H:" + currentEndHour.substring(2, currentEndHour.length());
	}

}