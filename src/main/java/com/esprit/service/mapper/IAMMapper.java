package com.esprit.service.mapper;

import java.util.List;

import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.dto.user.SpecificUserResponse;
import com.esprit.dto.user.UserResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAMMapper {

	IAMMapper INSTANCE = Mappers.getMapper(IAMMapper.class);

	@Mapping(target = "status", source = "enabled", qualifiedByName = "enabledToStatus")
	UserResponse userRepresentationToUserResponse(UserRepresentation usersRepresentation);

	List<UserResponse> usersRepresentationToUsersResponse(List<UserRepresentation> usersRepresentation);

	@Mapping(target = "username", source = "preferredUsername")
	@Mapping(target = "firstName", source = "givenName")
	@Mapping(target = "lastName", source = "familyName")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdTimestamp", ignore = true)
	UserResponse tokenToUserResponse(AccessToken token);

	SpecificUserResponse userResponseToSpecificUserResponse(UserResponse userResponse);

	List<SpecificUserResponse> usersResponseToSpecificUsersResponse(List<UserResponse> usersResponse);

	@AfterMapping
	default void setFullName(@MappingTarget SpecificUserResponse specificUserResponse, UserResponse userResponse) {
		specificUserResponse.setFullName(userResponse.getFirstName() + " " + userResponse.getLastName());
	}

	@Named("enabledToStatus")
	public default String enabledToStatus(final Boolean enabled) {
		return enabled.equals(Boolean.TRUE) ? "Activer" : "Désactiver";
	}

}