package com.esprit.service.impl;

import java.security.Principal;
import java.util.List;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Service;

import com.esprit.domain.StudentEntity;
import com.esprit.dto.student.CreateStudentRequest;
import com.esprit.dto.student.StudentDTO;
import com.esprit.dto.user.CreateUserRequest;
import com.esprit.dto.user.UserResponse;
import com.esprit.error.exception.EntityAlreadyExistsExeption;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.StudentRepository;
import com.esprit.service.IAMService;
import com.esprit.service.StudentService;
import com.esprit.service.mapper.StudentMapper;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

	private final ClassRepository classRepository;

	private final StudentMapper mapper;

	private final IAMService iamService;

	public StudentServiceImpl(StudentRepository studentRepository, ClassRepository classRepository,
			StudentMapper mapper, IAMService iamService) {
		this.studentRepository = studentRepository;
		this.classRepository = classRepository;
		this.mapper = mapper;
		this.iamService = iamService;
	}

	@Override
	public void addStudent(CreateStudentRequest createStudentRequest) {
		StudentEntity studentEntity = mapper.createStudentRequestToStudentEntity(createStudentRequest, classRepository);
		if (studentRepository.findByCin(createStudentRequest.getCin()) != null) {
			throw new EntityAlreadyExistsExeption(StudentEntity.class, "cin", createStudentRequest.getCin() + "");
		}
		if (studentRepository.findByEmail(createStudentRequest.getEmail()) != null) {
			throw new EntityAlreadyExistsExeption(StudentEntity.class, "email", createStudentRequest.getEmail());
		}
		studentRepository.save(studentEntity);
		iamService.addUser(new CreateUserRequest().role("STUDENT")
				.firstName(createStudentRequest.getFullName().contains(" ")
						? createStudentRequest.getFullName().split(" ")[0]
						: createStudentRequest.getFullName())
				.lastName(createStudentRequest.getFullName().contains(" ")
						? createStudentRequest.getFullName().split(" ")[1]
						: null)
				.email(createStudentRequest.getEmail()));
	}

	@Override
	public void updateStudent(CreateStudentRequest createStudentRequest) {
		StudentEntity studentEntity = mapper.createStudentRequestToStudentEntity(createStudentRequest, classRepository);
		if (studentRepository.findByCin(createStudentRequest.getCin()) != null) {
			throw new EntityAlreadyExistsExeption(StudentEntity.class, "cin", createStudentRequest.getCin() + "");
		}
		studentRepository.save(studentEntity);
	}

	@Override
	public List<StudentDTO> findAll() {
		return mapper.studentEntitiesToStudentDTO(studentRepository.findAll());
	}

	@Override
	public void deleteStudent(String studentId) {
		StudentEntity studentEntity = studentRepository.getOne(studentId);
		UserResponse userResponse = iamService.findUser(studentEntity.getEmail());
		iamService.deleteUser(userResponse.getId());
		studentRepository.deleteById(studentId);
	}

	@Override
	public StudentDTO findByPrincipal(Principal principal) {
		StudentDTO result = new StudentDTO();
		if (KeycloakAuthenticationToken.class.isAssignableFrom(principal.getClass())) {
			KeycloakAuthenticationToken authToken = (KeycloakAuthenticationToken) principal;
			AccessToken token = authToken.getAccount().getKeycloakSecurityContext().getToken();
			result = mapper.studentEntityToStudentDTO(studentRepository.findByEmail(token.getEmail()));
		}
		return result;
	}

}