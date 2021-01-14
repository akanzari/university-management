package com.esprit.service.mapper;

import com.esprit.domain.ClassRoomEntity;
import com.esprit.dto.request.rooms.CreateClassRoomRequest;
import com.esprit.dto.request.rooms.UpdateClassRoomRequest;
import com.esprit.dto.response.ClassRoomResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Private Build)"
)
@Component
public class ClassRoomMapperImpl implements ClassRoomMapper {

    @Override
    public ClassRoomEntity createClassRoomRequestToClassRoomEntity(CreateClassRoomRequest createClassRoomRequest) {
        if ( createClassRoomRequest == null ) {
            return null;
        }

        ClassRoomEntity classRoomEntity = new ClassRoomEntity();

        classRoomEntity.setLabel( createClassRoomRequest.getLabel() );
        classRoomEntity.setCapacity( createClassRoomRequest.getCapacity() );
        classRoomEntity.setStartDate( createClassRoomRequest.getStartDate() );
        classRoomEntity.setEndDate( createClassRoomRequest.getEndDate() );
        classRoomEntity.setStartHour( createClassRoomRequest.getStartHour() );
        classRoomEntity.setEndHour( createClassRoomRequest.getEndHour() );

        return classRoomEntity;
    }

    @Override
    public ClassRoomEntity updateClassRoomRequestToClassRoomEntity(UpdateClassRoomRequest updateClassRoomRequest) {
        if ( updateClassRoomRequest == null ) {
            return null;
        }

        ClassRoomEntity classRoomEntity = new ClassRoomEntity();

        classRoomEntity.setClassRoomId( updateClassRoomRequest.getClassRoomId() );
        classRoomEntity.setLabel( updateClassRoomRequest.getLabel() );
        classRoomEntity.setCapacity( updateClassRoomRequest.getCapacity() );
        classRoomEntity.setStartDate( updateClassRoomRequest.getStartDate() );
        classRoomEntity.setEndDate( updateClassRoomRequest.getEndDate() );
        classRoomEntity.setStartHour( updateClassRoomRequest.getStartHour() );
        classRoomEntity.setEndHour( updateClassRoomRequest.getEndHour() );

        return classRoomEntity;
    }

    @Override
    public ClassRoomResponse classRoomEntityToClassRoomResponse(ClassRoomEntity classRoomEntity) {
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

    @Override
    public List<ClassRoomResponse> classRoomEntitiesToClassRoomResponse(List<ClassRoomEntity> classRoomEntities) {
        if ( classRoomEntities == null ) {
            return null;
        }

        List<ClassRoomResponse> list = new ArrayList<ClassRoomResponse>( classRoomEntities.size() );
        for ( ClassRoomEntity classRoomEntity : classRoomEntities ) {
            list.add( classRoomEntityToClassRoomResponse( classRoomEntity ) );
        }

        return list;
    }
}
