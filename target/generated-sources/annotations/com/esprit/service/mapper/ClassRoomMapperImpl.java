package com.esprit.service.mapper;

import com.esprit.domain.ClassRoomEntity;
import com.esprit.domain.SiteEntity;
import com.esprit.dto.request.CreateClassRoomRequest;
import com.esprit.dto.request.UpdateClassRoomRequest;
import com.esprit.dto.response.ClassRoomResponse;
import com.esprit.dto.response.SiteResponse;
import com.esprit.repository.SiteRepository;
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
    public ClassRoomEntity createClassRoomRequestToClassRoomEntity(CreateClassRoomRequest createClassRoomRequest, SiteRepository repository) {
        if ( createClassRoomRequest == null ) {
            return null;
        }

        ClassRoomEntity classRoomEntity = new ClassRoomEntity();

        classRoomEntity.setCode( createClassRoomRequest.getCode() );
        classRoomEntity.setLabel( createClassRoomRequest.getLabel() );
        classRoomEntity.setCapacity( createClassRoomRequest.getCapacity() );

        ClassRoomMapper.after( createClassRoomRequest, classRoomEntity, repository );

        return classRoomEntity;
    }

    @Override
    public ClassRoomEntity updateClassRoomRequestToClassRoomEntity(UpdateClassRoomRequest updateClassRoomRequest, SiteRepository repository) {
        if ( updateClassRoomRequest == null ) {
            return null;
        }

        ClassRoomEntity classRoomEntity = new ClassRoomEntity();

        classRoomEntity.setClassRoomId( updateClassRoomRequest.getClassRoomId() );
        classRoomEntity.setLabel( updateClassRoomRequest.getLabel() );
        classRoomEntity.setCapacity( updateClassRoomRequest.getCapacity() );

        ClassRoomMapper.after( updateClassRoomRequest, classRoomEntity, repository );

        return classRoomEntity;
    }

    @Override
    public ClassRoomResponse classRoomEntityToClassRoomResponse(ClassRoomEntity classRoomEntity) {
        if ( classRoomEntity == null ) {
            return null;
        }

        ClassRoomResponse classRoomResponse = new ClassRoomResponse();

        classRoomResponse.setClassRoomId( classRoomEntity.getClassRoomId() );
        classRoomResponse.setCode( classRoomEntity.getCode() );
        classRoomResponse.setLabel( classRoomEntity.getLabel() );
        classRoomResponse.setCapacity( classRoomEntity.getCapacity() );
        classRoomResponse.setSites( siteEntityListToSiteResponseList( classRoomEntity.getSites() ) );
        classRoomResponse.setStartDate( classRoomEntity.getStartDate() );
        classRoomResponse.setEndDate( classRoomEntity.getEndDate() );
        classRoomResponse.setStartHour( classRoomEntity.getStartHour() );
        classRoomResponse.setEndHour( classRoomEntity.getEndHour() );
        classRoomResponse.setReason( classRoomEntity.getReason() );
        classRoomResponse.setCreatedDate( classRoomEntity.getCreatedDate() );

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

    protected SiteResponse siteEntityToSiteResponse(SiteEntity siteEntity) {
        if ( siteEntity == null ) {
            return null;
        }

        SiteResponse siteResponse = new SiteResponse();

        siteResponse.setSiteId( siteEntity.getSiteId() );
        siteResponse.setCode( siteEntity.getCode() );
        siteResponse.setLabel( siteEntity.getLabel() );

        return siteResponse;
    }

    protected List<SiteResponse> siteEntityListToSiteResponseList(List<SiteEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<SiteResponse> list1 = new ArrayList<SiteResponse>( list.size() );
        for ( SiteEntity siteEntity : list ) {
            list1.add( siteEntityToSiteResponse( siteEntity ) );
        }

        return list1;
    }
}
