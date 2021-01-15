package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.DisponibilityEntity;
import com.esprit.domain.RoomEntity;
import com.esprit.dto.DisponibilityDTO;
import com.esprit.dto.RoomDTO;
import com.esprit.enums.PeriodEnum;
import com.esprit.enums.PoleEnum;
import com.esprit.enums.SemesterEnum;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

	RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

	RoomEntity createClassRoomRequestToClassRoomEntity(RoomDTO roomDTO);

	RoomEntity updateClassRoomRequestToClassRoomEntity(RoomDTO roomDTO);

	@Mapping(target = "pole", source = "pole", qualifiedByName = "toPoleString")
	RoomDTO classRoomEntityToClassRoomResponse(RoomEntity classRoomEntity);

	List<RoomDTO> classRoomEntitiesToClassRoomResponse(List<RoomEntity> classRoomEntities);

	@Mapping(target = "period", source = "period", qualifiedByName = "toPeriodEnum")
	DisponibilityEntity createDisponibilityRequestToDisponibilityEntity(DisponibilityDTO disponibilityDTO);

	@Mapping(target = "period", source = "period", qualifiedByName = "toPeriodString")
	DisponibilityDTO disponibilityEntityToDisponibilityEntities(DisponibilityEntity disponibilityEntity);

	List<DisponibilityEntity> createDisponibilityRequestToDisponibilityEntities(
			List<DisponibilityDTO> createDisponibilityRequest);

	public default SemesterEnum toSemesterEnum(final String semester) {
		return SemesterEnum.forValue(semester);
	}

	public default PeriodEnum toPeriodEnum(final String period) {
		return PeriodEnum.forValue(period);
	}

	public default String toPoleString(final PoleEnum pole) {
		String result = null;
		if (pole != null) {
			result = PoleEnum.forKey(pole);
		}
		return result;
	}

	public default String toSemesterString(final SemesterEnum semester) {
		String result = null;
		if (semester != null) {
			result = SemesterEnum.forKey(semester);
		}
		return result;
	}

	public default String toPeriodString(final PeriodEnum period) {
		String result = null;
		if (period != null) {
			result = PeriodEnum.forKey(period);
		}
		return result;
	}

}