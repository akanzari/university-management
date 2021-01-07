package com.esprit.service.mapper;

import com.esprit.domain.DepartmentEntity;
import com.esprit.dto.request.CreateDepartmentRequest;
import com.esprit.dto.response.DepartmentResponse;
import com.esprit.repository.SiteRepository;
import com.esprit.repository.SpecialityRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Private Build)"
)
@Component
public class DepartmentMapperImpl implements DepartmentMapper {

    @Autowired
    private SpecialityMapper specialityMapper;

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

        departmentResponse.setDepartmentId( departmentEntity.getDepartmentId() );
        departmentResponse.setCode( departmentEntity.getCode() );
        departmentResponse.setLabel( departmentEntity.getLabel() );
        departmentResponse.setSpecialities( specialityMapper.specialityEntitiesToSpecialityResponse( departmentEntity.getSpecialities() ) );

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
}
