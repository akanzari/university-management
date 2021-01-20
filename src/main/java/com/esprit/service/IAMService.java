package com.esprit.service;

import java.security.Principal;
import java.util.List;

import com.esprit.dto.user.CreateUserRequest;
import com.esprit.dto.user.SpecificUserResponse;
import com.esprit.dto.user.UpdateUserRequest;
import com.esprit.dto.user.UserResponse;

public interface IAMService {

	String addUser(CreateUserRequest createUserRequest);

	void updateUser(UpdateUserRequest updateUserRequest);

	void disabledUser(String userId);

	void enabledUser(String userId);

	void deleteUser(String userId);

	String findFullNameById(String userId);

	UserResponse findUser(String email);

	Boolean isUserExist(String email);

	UserResponse findCurrentUser(Principal principal);

	List<UserResponse> findUsers();

	List<SpecificUserResponse> findUsersByRole(String role);

	List<String> findRoles();

}