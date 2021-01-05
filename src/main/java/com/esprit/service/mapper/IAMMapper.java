package com.esprit.service.mapper;

import java.util.List;

import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.esprit.dto.response.SpecificUserResponse;
import com.esprit.dto.response.UserResponse;

@Mapper
public interface IAMMapper {

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

}