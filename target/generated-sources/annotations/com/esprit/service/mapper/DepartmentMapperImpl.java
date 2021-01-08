package com.esprit.service.mapper;

import com.esprit.domain.DepartmentEntity;
import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.request.CreateDepartmentRequest;
import com.esprit.dto.response.DepartmentResponse;
import com.esprit.dto.response.SpecialityResponse;
import com.esprit.repository.SiteRepository;
import com.esprit.repository.SpecialityRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 1.3.1100.v20200828-0941, environment: Java 15 (Oracle Corporation)"
)
@Component
public class DepartmentMapperImpl implements DepartmentMapper {

    @Override
    public DepartmentEntity createDepartmentRequestToDepartmentEntity(CreateDepartmentRequest createDepartmentRoomRequest, SiteRepository siteRepository, SpecialityRepository specialityRepository) {
        if ( createDepartmentRoomRequest == null ) {
            return null;
        }

        DepartmentEntity departmentEntity = new DepartmentEntity();

        departmentEntity.setCode( createDepartmentRoomRequest.getCode() );
        departmentEntity.setLabel( createDepartmentRoomRequest.getLabel() );

        DepartmentMapper.after( createDepartmentRoomRequest, departmentEntity, siteRepository, specialityRepository );

        return departmentEntity;
    }

    @Override
    public DepartmentResponse departmentEntityToDepartmentResponse(DepartmentEntity departmentEntity) {
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

    @Override
    public List<DepartmentResponse> departmentEntitiesToDepartmentResponse(List<DepartmentEntity> departmentEntities) {
        if ( departmentEntities == null ) {
            return null;
        }

        List<DepartmentResponse> list = new ArrayList<DepartmentResponse>( departmentEntities.size() );
        for ( DepartmentEntity departmentEntity : departmentEntities ) {
            list.add( departmentEntityToDepartmentResponse( departmentEntity ) );
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
}
