package com.esprit.service.mapper;

import com.esprit.domain.ClassEntity;
import com.esprit.domain.DepartmentEntity;
import com.esprit.domain.ModuleEntity;
import com.esprit.domain.SpecialityEntity;
import com.esprit.domain.TeacherEntity;
import com.esprit.dto.request.CreateModuleRequest;
import com.esprit.dto.request.UpdateModuleRequest;
import com.esprit.dto.response.ClassResponse;
import com.esprit.dto.response.DepartmentResponse;
import com.esprit.dto.response.ModuleResponse;
import com.esprit.dto.response.SpecialityResponse;
import com.esprit.dto.response.TeacherResponse;
import com.esprit.enums.PeriodEnum;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.TeacherRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 1.3.1100.v20200828-0941, environment: Java 15 (Oracle Corporation)"
)
@Component
public class ModuleMapperImpl implements ModuleMapper {

    @Override
    public ModuleEntity createModuleRequestToModuleEntity(CreateModuleRequest createModuleRequest, TeacherRepository teacherRepository, ClassRepository classRepository) {
        if ( createModuleRequest == null ) {
            return null;
        }

        ModuleEntity moduleEntity = new ModuleEntity();

        moduleEntity.setCode( createModuleRequest.getCode() );
        moduleEntity.setCoefficient( createModuleRequest.getCoefficient() );
        moduleEntity.setDesignation( createModuleRequest.getDesignation() );
        moduleEntity.setExamType( createModuleRequest.getExamType() );
        moduleEntity.setNbrHours( createModuleRequest.getNbrHours() );
        List<PeriodEnum> list = createModuleRequest.getPeriods();
        if ( list != null ) {
            moduleEntity.setPeriods( new ArrayList<PeriodEnum>( list ) );
        }
        moduleEntity.setSemester( createModuleRequest.getSemester() );

        return moduleEntity;
    }

    @Override
    public ModuleEntity updateModuleRequestToModuleEntity(UpdateModuleRequest updateModuleRequest, TeacherRepository teacherRepository, ClassRepository classRepository) {
        if ( updateModuleRequest == null ) {
            return null;
        }

        ModuleEntity moduleEntity = new ModuleEntity();

        moduleEntity.setCoefficient( updateModuleRequest.getCoefficient() );
        moduleEntity.setExamType( updateModuleRequest.getExamType() );
        moduleEntity.setModuleId( updateModuleRequest.getModuleId() );
        moduleEntity.setNbrHours( updateModuleRequest.getNbrHours() );
        List<PeriodEnum> list = updateModuleRequest.getPeriods();
        if ( list != null ) {
            moduleEntity.setPeriods( new ArrayList<PeriodEnum>( list ) );
        }
        moduleEntity.setSemester( updateModuleRequest.getSemester() );

        return moduleEntity;
    }

    @Override
    public ModuleResponse moduleEntityToModuleResponse(ModuleEntity moduleEntity) {
        if ( moduleEntity == null ) {
            return null;
        }

        ModuleResponse moduleResponse = new ModuleResponse();

        moduleResponse.setClasss( classEntityToClassResponse( moduleEntity.getClasss() ) );
        moduleResponse.setCode( moduleEntity.getCode() );
        moduleResponse.setCoefficient( moduleEntity.getCoefficient() );
        moduleResponse.setDesignation( moduleEntity.getDesignation() );
        moduleResponse.setExamType( moduleEntity.getExamType() );
        moduleResponse.setModuleId( moduleEntity.getModuleId() );
        moduleResponse.setNbrHours( moduleEntity.getNbrHours() );
        List<PeriodEnum> list = moduleEntity.getPeriods();
        if ( list != null ) {
            moduleResponse.setPeriods( new ArrayList<PeriodEnum>( list ) );
        }
        moduleResponse.setSemester( moduleEntity.getSemester() );
        moduleResponse.setTeacher( teacherEntityToTeacherResponse( moduleEntity.getTeacher() ) );

        return moduleResponse;
    }

    @Override
    public List<ModuleResponse> moduleEntitiesToModuleResponse(List<ModuleEntity> moduleEntities) {
        if ( moduleEntities == null ) {
            return null;
        }

        List<ModuleResponse> list = new ArrayList<ModuleResponse>( moduleEntities.size() );
        for ( ModuleEntity moduleEntity : moduleEntities ) {
            list.add( moduleEntityToModuleResponse( moduleEntity ) );
        }

        return list;
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

    protected TeacherResponse teacherEntityToTeacherResponse(TeacherEntity teacherEntity) {
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
}
