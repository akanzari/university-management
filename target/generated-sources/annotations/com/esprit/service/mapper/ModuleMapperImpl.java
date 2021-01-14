package com.esprit.service.mapper;

import com.esprit.domain.AssignClassEntity;
import com.esprit.domain.ModuleEntity;
import com.esprit.dto.request.modules.AssignClassToModuleRequest;
import com.esprit.dto.request.modules.CreateModuleRequest;
import com.esprit.dto.response.ModuleResponse;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.TeacherRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Private Build)"
)
@Component
public class ModuleMapperImpl implements ModuleMapper {

    @Override
    public ModuleEntity createModuleRequestToModuleEntity(CreateModuleRequest createModuleRequest, TeacherRepository teacherRepository, ClassRepository classRepository) {
        if ( createModuleRequest == null ) {
            return null;
        }

        ModuleEntity moduleEntity = new ModuleEntity();

        moduleEntity.setModuleId( createModuleRequest.getModuleId() );
        moduleEntity.setDesignation( createModuleRequest.getDesignation() );
        moduleEntity.setAssignClasses( assignClassToModuleRequestListToAssignClassEntityList( createModuleRequest.getAssignClasses(), teacherRepository, classRepository ) );

        ModuleMapper.after( createModuleRequest, moduleEntity, teacherRepository, classRepository );

        return moduleEntity;
    }

    @Override
    public ModuleResponse moduleEntityToModuleResponse(ModuleEntity moduleEntity) {
        if ( moduleEntity == null ) {
            return null;
        }

        ModuleResponse moduleResponse = new ModuleResponse();

        moduleResponse.setModuleId( moduleEntity.getModuleId() );
        moduleResponse.setDesignation( moduleEntity.getDesignation() );

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

    protected AssignClassEntity assignClassToModuleRequestToAssignClassEntity(AssignClassToModuleRequest assignClassToModuleRequest, TeacherRepository teacherRepository, ClassRepository classRepository) {
        if ( assignClassToModuleRequest == null ) {
            return null;
        }

        AssignClassEntity assignClassEntity = new AssignClassEntity();

        assignClassEntity.coefficient( assignClassToModuleRequest.getCoefficient() );
        assignClassEntity.nbrHours( assignClassToModuleRequest.getNbrHours() );
        assignClassEntity.semester( ModuleMapper.toSemesterEnum( assignClassToModuleRequest.getSemester() ) );
        assignClassEntity.periods( ModuleMapper.toPeriodEnum( assignClassToModuleRequest.getPeriods() ) );
        assignClassEntity.examType( ModuleMapper.toExamTypeEnum( assignClassToModuleRequest.getExamType() ) );

        return assignClassEntity;
    }

    protected List<AssignClassEntity> assignClassToModuleRequestListToAssignClassEntityList(List<AssignClassToModuleRequest> list, TeacherRepository teacherRepository, ClassRepository classRepository) {
        if ( list == null ) {
            return null;
        }

        List<AssignClassEntity> list1 = new ArrayList<AssignClassEntity>( list.size() );
        for ( AssignClassToModuleRequest assignClassToModuleRequest : list ) {
            list1.add( assignClassToModuleRequestToAssignClassEntity( assignClassToModuleRequest, teacherRepository, classRepository ) );
        }

        return list1;
    }
}
