package com.esprit.service.mapper;

import com.esprit.domain.ClassEntity;
import com.esprit.domain.DepartmentEntity;
import com.esprit.domain.SpecialityEntity;
import com.esprit.domain.StudentEntity;
import com.esprit.domain.TeacherEntity;
import com.esprit.dto.request.CreateStudentRequest;
import com.esprit.dto.request.CreateTeacherRequest;
import com.esprit.dto.response.ClassResponse;
import com.esprit.dto.response.DepartmentResponse;
import com.esprit.dto.response.SpecialityResponse;
import com.esprit.dto.response.StudentResponse;
import com.esprit.dto.response.TeacherResponse;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.DepartmentRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 1.3.1100.v20200828-0941, environment: Java 15 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public StudentEntity createStudentRequestToStudentEntity(CreateStudentRequest createStudentRequest, ClassRepository classRepository) {
        if ( createStudentRequest == null ) {
            return null;
        }

        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setCin( createStudentRequest.getCin() );
        studentEntity.setUserId( createStudentRequest.getUserId() );

        UserMapper.after( createStudentRequest, studentEntity, classRepository );

        return studentEntity;
    }

    @Override
    public StudentResponse studentEntityToStudentResponse(StudentEntity studentEntity) {
        if ( studentEntity == null ) {
            return null;
        }

        StudentResponse studentResponse = new StudentResponse();

        studentResponse.setCin( studentEntity.getCin() );
        studentResponse.setClasss( classEntityToClassResponse( studentEntity.getClasss() ) );
        studentResponse.setUserId( studentEntity.getUserId() );

        return studentResponse;
    }

    @Override
    public TeacherEntity createTeacherRequestoTeacherEntity(CreateTeacherRequest createTeacherRequest, DepartmentRepository departmentRepository) {
        if ( createTeacherRequest == null ) {
            return null;
        }

        TeacherEntity teacherEntity = new TeacherEntity();

        teacherEntity.setPhone( createTeacherRequest.getPhone() );
        teacherEntity.setUserId( createTeacherRequest.getUserId() );

        UserMapper.after( createTeacherRequest, teacherEntity, departmentRepository );

        return teacherEntity;
    }

    @Override
    public TeacherResponse teacherEntityToTeacherResponse(TeacherEntity teacherEntity) {
        if ( teacherEntity == null ) {
            return null;
        }

        TeacherResponse teacherResponse = new TeacherResponse();

        teacherResponse.setDepartment( departmentEntityToDepartmentResponse( teacherEntity.getDepartment() ) );
        teacherResponse.setEndDate( teacherEntity.getEndDate() );
        teacherResponse.setNbrHeureSurveillance( teacherEntity.getNbrHeureSurveillance() );
        teacherResponse.setNbrSurveillance( teacherEntity.getNbrSurveillance() );
        teacherResponse.setPhone( teacherEntity.getPhone() );
        teacherResponse.setReason( teacherEntity.getReason() );
        teacherResponse.setStartDate( teacherEntity.getStartDate() );
        teacherResponse.setUserId( teacherEntity.getUserId() );

        return teacherResponse;
    }

    protected SpecialityResponse specialityEntityToSpecialityResponse(SpecialityEntity specialityEntity) {
        if ( specialityEntity == null ) {
            return null;
        }

        SpecialityResponse specialityResponse = new SpecialityResponse();

        specialityResponse.setCode( specialityEntity.getCode() );
        specialityResponse.setLabel( specialityEntity.getLabel() );
        specialityResponse.setSpecialityId( specialityEntity.getSpecialityId() );

        return specialityResponse;
    }

    protected ClassResponse classEntityToClassResponse(ClassEntity classEntity) {
        if ( classEntity == null ) {
            return null;
        }

        ClassResponse classResponse = new ClassResponse();

        classResponse.setClassId( classEntity.getClassId() );
        classResponse.setCode( classEntity.getCode() );
        classResponse.setCreatedDate( classEntity.getCreatedDate() );
        classResponse.setLabel( classEntity.getLabel() );
        classResponse.setNbrGroups( classEntity.getNbrGroups() );
        classResponse.setNbrStudents( classEntity.getNbrStudents() );
        classResponse.setSpeciality( specialityEntityToSpecialityResponse( classEntity.getSpeciality() ) );

        return classResponse;
    }

    protected List<SpecialityResponse> specialityEntityListToSpecialityResponseList(List<SpecialityEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<SpecialityResponse> list1 = new ArrayList<SpecialityResponse>( list.size() );
        for ( SpecialityEntity specialityEntity : list ) {
            list1.add( specialityEntityToSpecialityResponse( specialityEntity ) );
        }

        return list1;
    }

    protected DepartmentResponse departmentEntityToDepartmentResponse(DepartmentEntity departmentEntity) {
        if ( departmentEntity == null ) {
            return null;
        }

        DepartmentResponse departmentResponse = new DepartmentResponse();

        departmentResponse.setCode( departmentEntity.getCode() );
        departmentResponse.setDepartmentId( departmentEntity.getDepartmentId() );
        departmentResponse.setLabel( departmentEntity.getLabel() );
        departmentResponse.setSpecialities( specialityEntityListToSpecialityResponseList( departmentEntity.getSpecialities() ) );

        return departmentResponse;
    }
}
