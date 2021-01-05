package com.esprit.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessToken.Access;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.esprit.dto.request.CreateUserRequest;
import com.esprit.dto.request.UpdateUserRequest;
import com.esprit.dto.response.SpecificUserResponse;
import com.esprit.dto.response.UserResponse;
import com.esprit.service.IAMService;
import com.esprit.service.mapper.IAMMapper;
import com.esprit.util.RandomPasswordGenerator;

@Service
public class IAMServiceImpl implements IAMService {

	@Qualifier("keycloak-client")
	private final Keycloak keycloakClient;

	private final IAMMapper mapper;

	@Value("${keycloak.realm}")
	String realm;

	public IAMServiceImpl(Keycloak keycloakClient, IAMMapper mapper) {
		this.keycloakClient = keycloakClient;
		this.mapper = mapper;
	}

	@Override
	public void addUser(CreateUserRequest createUserRequest) {
		String username = createUserRequest.getFirstName().toLowerCase() + "_"
				+ createUserRequest.getLastName().toLowerCase();
		if (findUser(username) == null) {
			keycloakClient.tokenManager().getAccessToken();
			UserRepresentation user = new UserRepresentation();
			user.setEnabled(true);
			user.setFirstName(createUserRequest.getFirstName());
			user.setLastName(createUserRequest.getLastName());
			user.setEmail(createUserRequest.getEmail());
			user.setUsername(username);
			RealmResource realmResource = keycloakClient.realm(realm);
			UsersResource usersRessource = realmResource.users();
			Response response = usersRessource.create(user);
			if (response.getStatus() == 201) {
				String userId = CreatedResponseUtil.getCreatedId(response);
				CredentialRepresentation passwordCred = new CredentialRepresentation();
				passwordCred.setTemporary(false);
				passwordCred.setType(CredentialRepresentation.PASSWORD);
				passwordCred.setValue(RandomPasswordGenerator.generatePassayPassword());
				UserResource userResource = usersRessource.get(userId);
				userResource.resetPassword(passwordCred);
				RoleRepresentation realmRoleUser = realmResource.roles().get(createUserRequest.getRole())
						.toRepresentation();
				userResource.roles().realmLevel().add(Arrays.asList(realmRoleUser));
			}
		}
	}

	@Override
	public void updateUser(UpdateUserRequest updateUserRequest) {
		RealmResource realmResource = keycloakClient.realm(realm);
		UserResource userRessource = realmResource.users().get(updateUserRequest.getId());
		UserRepresentation user = userRessource.toRepresentation();
		user.setEmail(updateUserRequest.getEmail());
		userRessource.update(user);

		List<RoleRepresentation> rolesUser = realmResource.roles().list();
		List<RoleRepresentation> listRole = rolesUser.stream()
				.filter(role -> !role.getName().equals("uma_authorization") && !role.getName().equals("offline_access"))
				.collect(Collectors.toList());
		userRessource.roles().realmLevel().remove(listRole);

		RoleRepresentation realmRoleUser = realmResource.roles().get(updateUserRequest.getRole()).toRepresentation();
		userRessource.roles().realmLevel().add(Arrays.asList(realmRoleUser));
	}

	@Override
	public void deleteUser(String userId) {
		keycloakClient.tokenManager().getAccessToken();
		RealmResource realmResource = keycloakClient.realm(realm);
		UsersResource usersRessource = realmResource.users();
		usersRessource.delete(userId);
	}

	@Override
	public List<UserResponse> findUsers() {
		List<UserResponse> result = new ArrayList<>();
		RealmResource realmResource = keycloakClient.realm(realm);
		UsersResource usersRessource = realmResource.users();
		for (UserRepresentation user : usersRessource.list()) {
			UserResponse userResponse = mapper.userRepresentationToUserResponse(user);
			UserResource userResource = usersRessource.get(user.getId());
			List<RoleRepresentation> roles = userResource.roles().realmLevel().listAll();
			if (CollectionUtils.isNotEmpty(roles)) {
				Set<String> listRole = roles.stream().map(RoleRepresentation::getName)
						.filter(role -> !role.equals("uma_authorization") && !role.equals("offline_access"))
						.collect(Collectors.toSet());
				userResponse.roles(listRole);
			}
			result.add(userResponse);
		}
		return result;
	}

	@Override
	public UserResponse findUser(String username) {
		UserResponse result = null;
		RealmResource realmResource = keycloakClient.realm(realm);
		UsersResource usersRessource = realmResource.users();
		List<UserRepresentation> users = usersRessource.search(username, true);
		if (CollectionUtils.isNotEmpty(users)) {
			UserRepresentation user = users.get(0);
			result = mapper.userRepresentationToUserResponse(user);
			UserResource userResource = usersRessource.get(user.getId());
			List<RoleRepresentation> roles = userResource.roles().realmLevel().listAll();
			if (CollectionUtils.isNotEmpty(roles)) {
				Set<String> listRole = roles.stream().map(RoleRepresentation::getName)
						.filter(role -> !role.equals("uma_authorization") && !role.equals("offline_access"))
						.collect(Collectors.toSet());
				result.roles(listRole);
			}
		}
		return result;
	}

	@Override
	public List<String> findRoles() {
		RealmResource realmResource = keycloakClient.realm(realm);
		RolesResource usersRessource = realmResource.roles();
		return usersRessource.list().stream().map(RoleRepresentation::getName)
				.filter(role -> !role.equals("uma_authorization") && !role.equals("offline_access"))
				.collect(Collectors.toList());
	}

	@Override
	public UserResponse findCurrentUser(Principal principal) {
		UserResponse result = new UserResponse();
		if (KeycloakAuthenticationToken.class.isAssignableFrom(principal.getClass())) {
			KeycloakAuthenticationToken authToken = (KeycloakAuthenticationToken) principal;
			AccessToken token = authToken.getAccount().getKeycloakSecurityContext().getToken();
			result = mapper.tokenToUserResponse(token);
			Access realmAccess = token.getRealmAccess();
			Set<String> roles = realmAccess.getRoles();
			if (CollectionUtils.isNotEmpty(roles)) {
				result.roles(roles);
			}
		}
		return result;
	}

	@Override
	public List<SpecificUserResponse> findUsersByRole(String role) {
		List<UserResponse> usersResponse = findUsers().stream().filter(user -> user.getRoles().contains(role))
				.collect(Collectors.toList());
		return mapper.usersResponseToSpecificUsersResponse(usersResponse);
	}

}