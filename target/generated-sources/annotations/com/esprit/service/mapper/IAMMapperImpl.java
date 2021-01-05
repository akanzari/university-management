package com.esprit.service.mapper;

import com.esprit.dto.response.SpecificUserResponse;
import com.esprit.dto.response.UserResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Private Build)"
)
@Component
public class IAMMapperImpl implements IAMMapper {

    @Override
    public UserResponse userRepresentationToUserResponse(UserRepresentation usersRepresentation) {
        if ( usersRepresentation == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.id( usersRepresentation.getId() );
        userResponse.username( usersRepresentation.getUsername() );
        userResponse.firstName( usersRepresentation.getFirstName() );
        userResponse.lastName( usersRepresentation.getLastName() );
        userResponse.email( usersRepresentation.getEmail() );
        if ( usersRepresentation.getCreatedTimestamp() != null ) {
            userResponse.createdTimestamp( String.valueOf( usersRepresentation.getCreatedTimestamp() ) );
        }

        return userResponse;
    }

    @Override
    public List<UserResponse> usersRepresentationToUsersResponse(List<UserRepresentation> usersRepresentation) {
        if ( usersRepresentation == null ) {
            return null;
        }

        List<UserResponse> list = new ArrayList<UserResponse>( usersRepresentation.size() );
        for ( UserRepresentation userRepresentation : usersRepresentation ) {
            list.add( userRepresentationToUserResponse( userRepresentation ) );
        }

        return list;
    }

    @Override
    public UserResponse tokenToUserResponse(AccessToken token) {
        if ( token == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.firstName( token.getGivenName() );
        userResponse.lastName( token.getFamilyName() );
        userResponse.username( token.getPreferredUsername() );
        userResponse.email( token.getEmail() );

        return userResponse;
    }

    @Override
    public SpecificUserResponse userResponseToSpecificUserResponse(UserResponse userResponse) {
        if ( userResponse == null ) {
            return null;
        }

        SpecificUserResponse specificUserResponse = new SpecificUserResponse();

        specificUserResponse.setId( userResponse.getId() );
        specificUserResponse.setEmail( userResponse.getEmail() );

        setFullName( specificUserResponse, userResponse );

        return specificUserResponse;
    }

    @Override
    public List<SpecificUserResponse> usersResponseToSpecificUsersResponse(List<UserResponse> usersResponse) {
        if ( usersResponse == null ) {
            return null;
        }

        List<SpecificUserResponse> list = new ArrayList<SpecificUserResponse>( usersResponse.size() );
        for ( UserResponse userResponse : usersResponse ) {
            list.add( userResponseToSpecificUserResponse( userResponse ) );
        }

        return list;
    }
}
