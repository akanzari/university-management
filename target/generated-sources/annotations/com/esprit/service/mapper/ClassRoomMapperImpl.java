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
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 1.3.1100.v20200828-0941, environment: Java 15 (Oracle Corporation)"
)
@Component
public class ClassRoomMapperImpl implements ClassRoomMapper {

    @Override
    public ClassRoomEntity createClassRoomRequestToClassRoomEntity(CreateClassRoomRequest createClassRoomRequest, SiteRepository repository) {
        if ( createClassRoomRequest == null ) {
            return null;
        }

        ClassRoomEntity classRoomEntity = new ClassRoomEntity();

        classRoomEntity.setCapacity( createClassRoomRequest.getCapacity() );
        classRoomEntity.setCode( createClassRoomRequest.getCode() );
        classRoomEntity.setLabel( createClassRoomRequest.getLabel() );

        ClassRoomMapper.after( createClassRoomRequest, classRoomEntity, repository );

        return classRoomEntity;
    }

    @Override
    public ClassRoomEntity updateClassRoomRequestToClassRoomEntity(UpdateClassRoomRequest updateClassRoomRequest, SiteRepository repository) {
        if ( updateClassRoomRequest == null ) {
            return null;
        }

        ClassRoomEntity classRoomEntity = new ClassRoomEntity();

        classRoomEntity.setCapacity( updateClassRoomRequest.getCapacity() );
        classRoomEntity.setClassRoomId( updateClassRoomRequest.getClassRoomId() );
        classRoomEntity.setEndDate( updateClassRoomRequest.getEndDate() );
        classRoomEntity.setEndHour( updateClassRoomRequest.getEndHour() );
        classRoomEntity.setLabel( updateClassRoomRequest.getLabel() );
        classRoomEntity.setReason( updateClassRoomRequest.getReason() );
        classRoomEntity.setStartDate( updateClassRoomRequest.getStartDate() );
        classRoomEntity.setStartHour( updateClassRoomRequest.getStartHour() );

        ClassRoomMapper.after( updateClassRoomRequest, classRoomEntity, repository );

        return classRoomEntity;
    }

    @Override
    public ClassRoomResponse classRoomEntityToClassRoomResponse(ClassRoomEntity classRoomEntity) {
        if ( classRoomEntity == null ) {
            return null;
        }

        ClassRoomResponse classRoomResponse = new ClassRoomResponse();

        classRoomResponse.setCapacity( classRoomEntity.getCapacity() );
        classRoomResponse.setClassRoomId( classRoomEntity.getClassRoomId() );
        classRoomResponse.setCode( classRoomEntity.getCode() );
        classRoomResponse.setCreatedDate( classRoomEntity.getCreatedDate() );
        classRoomResponse.setEndDate( classRoomEntity.getEndDate() );
        classRoomResponse.setEndHour( classRoomEntity.getEndHour() );
        classRoomResponse.setLabel( classRoomEntity.getLabel() );
        classRoomResponse.setReason( classRoomEntity.getReason() );
        classRoomResponse.setSites( siteEntityListToSiteResponseList( classRoomEntity.getSites() ) );
        classRoomResponse.setStartDate( classRoomEntity.getStartDate() );
        classRoomResponse.setStartHour( classRoomEntity.getStartHour() );

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

        siteResponse.setCode( siteEntity.getCode() );
        siteResponse.setLabel( siteEntity.getLabel() );
        siteResponse.setSiteId( siteEntity.getSiteId() );

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
