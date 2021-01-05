package com.esprit.service;

import java.security.Principal;
import java.util.List;

import com.esprit.dto.request.CreateUserRequest;
import com.esprit.dto.request.UpdateUserRequest;
import com.esprit.dto.response.SpecificUserResponse;
import com.esprit.dto.response.UserResponse;

public interface IAMService {

	void addUser(CreateUserRequest createUserRequest);

	void updateUser(UpdateUserRequest updateUserRequest);

	void deleteUser(String userId);

	UserResponse findUser(String username);

	UserResponse findCurrentUser(Principal principal);

	List<UserResponse> findUsers();

	List<SpecificUserResponse> findUsersByRole(String role);

	List<String> findRoles();

}