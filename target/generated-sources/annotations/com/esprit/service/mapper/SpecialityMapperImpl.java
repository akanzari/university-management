package com.esprit.service.mapper;

import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.request.CreateSpecialityRequest;
import com.esprit.dto.response.SpecialityResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Private Build)"
)
@Component
public class SpecialityMapperImpl implements SpecialityMapper {

    @Override
    public SpecialityEntity createSpecialityRequestToSpecialityEntity(CreateSpecialityRequest createClassRoomRequest) {
        if ( createClassRoomRequest == null ) {
            return null;
        }

        SpecialityEntity specialityEntity = new SpecialityEntity();

        specialityEntity.setCode( createClassRoomRequest.getCode() );
        specialityEntity.setLabel( createClassRoomRequest.getLabel() );

        return specialityEntity;
    }

    @Override
    public SpecialityResponse specialityEntityToSpecialityResponse(SpecialityEntity specialityEntity) {
        if ( specialityEntity == null ) {
            return null;
        }

        SpecialityResponse specialityResponse = new SpecialityResponse();

        specialityResponse.setSpecialityId( specialityEntity.getSpecialityId() );
        specialityResponse.setCode( specialityEntity.getCode() );
        specialityResponse.setLabel( specialityEntity.getLabel() );

        return specialityResponse;
    }

    @Override
    public List<SpecialityResponse> specialityEntitiesToSpecialityResponse(List<SpecialityEntity> specialityEntities) {
        if ( specialityEntities == null ) {
            return null;
        }

        List<SpecialityResponse> list = new ArrayList<SpecialityResponse>( specialityEntities.size() );
        for ( SpecialityEntity specialityEntity : specialityEntities ) {
            list.add( specialityEntityToSpecialityResponse( specialityEntity ) );
        }

        return list;
    }
}
