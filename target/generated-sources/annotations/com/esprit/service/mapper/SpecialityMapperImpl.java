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
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 1.3.1100.v20200828-0941, environment: Java 15 (Oracle Corporation)"
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

        specialityResponse.setCode( specialityEntity.getCode() );
        specialityResponse.setLabel( specialityEntity.getLabel() );
        specialityResponse.setSpecialityId( specialityEntity.getSpecialityId() );

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
