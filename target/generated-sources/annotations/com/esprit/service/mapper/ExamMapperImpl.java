package com.esprit.service.mapper;

import com.esprit.domain.ClassEntity;
import com.esprit.domain.ClassRoomEntity;
import com.esprit.domain.ExamEntity;
import com.esprit.domain.ModuleEntity;
import com.esprit.domain.TeacherEntity;
import com.esprit.dto.ClassDTO;
import com.esprit.dto.TeacherDTO;
import com.esprit.dto.request.exams.CreateExamRequest;
import com.esprit.dto.request.exams.UpdateExamRequest;
import com.esprit.dto.response.ClassRoomResponse;
import com.esprit.dto.response.ExamResponse;
import com.esprit.dto.response.ModuleResponse;
import com.esprit.enums.DsexEnum;
import com.esprit.enums.ExamTypeEnum;
import com.esprit.enums.SemesterEnum;
import com.esprit.enums.SessionEnum;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.ClassRoomRepository;
import com.esprit.repository.ModuleRepository;
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
public class ExamMapperImpl implements ExamMapper {

    @Override
    public ExamEntity createExamRequestToExamEntity(CreateExamRequest createExamRequest, ClassRepository classRepository, ModuleRepository moduleRepository, ClassRoomRepository classRoomRepository, TeacherRepository teacherRepository) {
        if ( createExamRequest == null ) {
            return null;
        }

        ExamEntity examEntity = new ExamEntity();

        examEntity.setExamDate( createExamRequest.getExamDate() );
        examEntity.setExamHour( createExamRequest.getExamHour() );
        examEntity.setExamDuration( createExamRequest.getExamDuration() );
        if ( createExamRequest.getExamType() != null ) {
            examEntity.setExamType( Enum.valueOf( ExamTypeEnum.class, createExamRequest.getExamType() ) );
        }
        if ( createExamRequest.getSemester() != null ) {
            examEntity.setSemester( Enum.valueOf( SemesterEnum.class, createExamRequest.getSemester() ) );
        }
        if ( createExamRequest.getExamSession() != null ) {
            examEntity.setExamSession( Enum.valueOf( SessionEnum.class, createExamRequest.getExamSession() ) );
        }
        if ( createExamRequest.getDsex() != null ) {
            examEntity.setDsex( Enum.valueOf( DsexEnum.class, createExamRequest.getDsex() ) );
        }

        ExamMapper.after( createExamRequest, examEntity, classRepository, moduleRepository, classRoomRepository, teacherRepository );

        return examEntity;
    }

    @Override
    public ExamEntity updateExamRequestToExamEntity(UpdateExamRequest updateExamRequest, ClassRepository classRepository, ModuleRepository moduleRepository, ClassRoomRepository classRoomRepository, TeacherRepository teacherRepository) {
        if ( updateExamRequest == null ) {
            return null;
        }

        ExamEntity examEntity = new ExamEntity();

        examEntity.setExamId( updateExamRequest.getExamId() );
        examEntity.setExamDate( updateExamRequest.getExamDate() );
        examEntity.setExamHour( updateExamRequest.getExamHour() );
        examEntity.setExamDuration( updateExamRequest.getExamDuration() );
        if ( updateExamRequest.getExamType() != null ) {
            examEntity.setExamType( Enum.valueOf( ExamTypeEnum.class, updateExamRequest.getExamType() ) );
        }
        if ( updateExamRequest.getSemester() != null ) {
            examEntity.setSemester( Enum.valueOf( SemesterEnum.class, updateExamRequest.getSemester() ) );
        }
        if ( updateExamRequest.getExamSession() != null ) {
            examEntity.setExamSession( Enum.valueOf( SessionEnum.class, updateExamRequest.getExamSession() ) );
        }
        if ( updateExamRequest.getDsex() != null ) {
            examEntity.setDsex( Enum.valueOf( DsexEnum.class, updateExamRequest.getDsex() ) );
        }

        ExamMapper.after( updateExamRequest, examEntity, classRepository, moduleRepository, classRoomRepository, teacherRepository );

        return examEntity;
    }

    @Override
    public ExamResponse examEntityToExamResponse(ExamEntity examEntity) {
        if ( examEntity == null ) {
            return null;
        }

        ExamResponse examResponse = new ExamResponse();

        examResponse.setExamId( examEntity.getExamId() );
        examResponse.setExamDate( examEntity.getExamDate() );
        examResponse.setExamHour( examEntity.getExamHour() );
        examResponse.setExamDuration( examEntity.getExamDuration() );
        if ( examEntity.getExamType() != null ) {
            examResponse.setExamType( examEntity.getExamType().name() );
        }
        if ( examEntity.getSemester() != null ) {
            examResponse.setSemester( examEntity.getSemester().name() );
        }
        if ( examEntity.getExamSession() != null ) {
            examResponse.setExamSession( examEntity.getExamSession().name() );
        }
        if ( examEntity.getDsex() != null ) {
            examResponse.setDsex( examEntity.getDsex().name() );
        }
        examResponse.setClasss( classEntityToClassDTO( examEntity.getClasss() ) );
        examResponse.setModule( moduleEntityToModuleResponse( examEntity.getModule() ) );
        examResponse.setClassRoom( classRoomEntityToClassRoomResponse( examEntity.getClassRoom() ) );
        examResponse.setSupervisor( teacherEntityToTeacherDTO( examEntity.getSupervisor() ) );

        return examResponse;
    }

    @Override
    public List<ExamResponse> examEntitiessToExamResponse(List<ExamEntity> examEntities) {
        if ( examEntities == null ) {
            return null;
        }

        List<ExamResponse> list = new ArrayList<ExamResponse>( examEntities.size() );
        for ( ExamEntity examEntity : examEntities ) {
            list.add( examEntityToExamResponse( examEntity ) );
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

    protected ModuleResponse moduleEntityToModuleResponse(ModuleEntity moduleEntity) {
        if ( moduleEntity == null ) {
            return null;
        }

        ModuleResponse moduleResponse = new ModuleResponse();

        moduleResponse.setModuleId( moduleEntity.getModuleId() );
        moduleResponse.setDesignation( moduleEntity.getDesignation() );

        return moduleResponse;
    }

    protected ClassRoomResponse classRoomEntityToClassRoomResponse(ClassRoomEntity classRoomEntity) {
        if ( classRoomEntity == null ) {
            return null;
        }

        ClassRoomResponse classRoomResponse = new ClassRoomResponse();

        classRoomResponse.setClassRoomId( classRoomEntity.getClassRoomId() );
        classRoomResponse.setLabel( classRoomEntity.getLabel() );
        classRoomResponse.setCapacity( classRoomEntity.getCapacity() );
        classRoomResponse.setBloc( classRoomEntity.getBloc() );
        classRoomResponse.setSite( classRoomEntity.getSite() );
        classRoomResponse.setStartDate( classRoomEntity.getStartDate() );
        classRoomResponse.setEndDate( classRoomEntity.getEndDate() );
        classRoomResponse.setStartHour( String.valueOf( classRoomEntity.getStartHour() ) );
        classRoomResponse.setEndHour( String.valueOf( classRoomEntity.getEndHour() ) );
        classRoomResponse.setReason( classRoomEntity.getReason() );

        return classRoomResponse;
    }

    protected TeacherDTO teacherEntityToTeacherDTO(TeacherEntity teacherEntity) {
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

        return teacherDTO;
    }
}
