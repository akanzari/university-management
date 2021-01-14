package com.esprit.service.mapper;

import com.esprit.domain.ClassEntity;
import com.esprit.domain.StudentEntity;
import com.esprit.domain.TeacherEntity;
import com.esprit.dto.ClassDTO;
import com.esprit.dto.TeacherDTO;
import com.esprit.dto.request.CreateStudentRequest;
import com.esprit.dto.response.StudentResponse;
import com.esprit.repository.ClassRepository;
import com.esprit.service.IAMService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Private Build)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public StudentEntity createStudentRequestToStudentEntity(CreateStudentRequest createStudentRequest, ClassRepository classRepository) {
        if ( createStudentRequest == null ) {
            return null;
        }

        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setUserId( createStudentRequest.getUserId() );
        studentEntity.setCin( createStudentRequest.getCin() );

        UserMapper.after( createStudentRequest, studentEntity, classRepository );

        return studentEntity;
    }

    @Override
    public StudentResponse studentEntityToStudentResponse(StudentEntity studentEntity) {
        if ( studentEntity == null ) {
            return null;
        }

        StudentResponse studentResponse = new StudentResponse();

        studentResponse.setUserId( studentEntity.getUserId() );
        studentResponse.setFullName( studentEntity.getFullName() );
        studentResponse.setCin( String.valueOf( studentEntity.getCin() ) );
        studentResponse.setClasss( classEntityToClassDTO( studentEntity.getClasss() ) );

        return studentResponse;
    }

    @Override
    public TeacherEntity teacherDTOtoTeacherEntity(TeacherDTO teacherDTO) {
        if ( teacherDTO == null ) {
            return null;
        }

        TeacherEntity teacherEntity = new TeacherEntity();

        teacherEntity.setTeacherId( teacherDTO.getTeacherId() );
        teacherEntity.setCin( teacherDTO.getCin() );
        teacherEntity.setSex( teacherDTO.getSex() );
        List<String> list = teacherDTO.getPhones();
        if ( list != null ) {
            teacherEntity.setPhones( new ArrayList<String>( list ) );
        }
        teacherEntity.setUp( teacherDTO.getUp() );

        return teacherEntity;
    }

    @Override
    public TeacherDTO teacherEntityToTeacherDTO(TeacherEntity teacherEntity, IAMService iamService) {
        if ( teacherEntity == null ) {
            return null;
        }

        TeacherDTO teacherDTO = new TeacherDTO();

        teacherDTO.setTeacherId( teacherEntity.getTeacherId() );
        teacherDTO.setCin( teacherEntity.getCin() );
        teacherDTO.setSex( teacherEntity.getSex() );
        List<String> list = teacherEntity.getPhones();
        if ( list != null ) {
            teacherDTO.setPhones( new ArrayList<String>( list ) );
        }
        teacherDTO.setUp( teacherEntity.getUp() );

        UserMapper.after( teacherEntity, teacherDTO, iamService );

        return teacherDTO;
    }

    @Override
    public List<TeacherDTO> teacherEntitiesToTeacherDTO(List<TeacherEntity> teacherEntities, IAMService iamService) {
        if ( teacherEntities == null ) {
            return null;
        }

        List<TeacherDTO> list = new ArrayList<TeacherDTO>( teacherEntities.size() );
        for ( TeacherEntity teacherEntity : teacherEntities ) {
            list.add( teacherEntityToTeacherDTO( teacherEntity, iamService ) );
        }

        return list;
    }

    protected ClassDTO classEntityToClassDTO(ClassEntity classEntity) {
        if ( classEntity == null ) {
            return null;
        }

        ClassDTO classDTO = new ClassDTO();

        classDTO.setClassId( classEntity.getClassId() );
        classDTO.setLabel( classEntity.getLabel() );
        classDTO.setNbrStudents( classEntity.getNbrStudents() );
        classDTO.setSpeciality( classEntity.getSpeciality() );
        classDTO.setCategory( classEntity.getCategory() );
        classDTO.setEmail( classEntity.getEmail() );

        return classDTO;
    }
}
