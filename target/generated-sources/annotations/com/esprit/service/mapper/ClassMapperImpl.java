package com.esprit.service.mapper;

import com.esprit.domain.ClassEntity;
import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.request.CreateClassRequest;
import com.esprit.dto.response.ClassResponse;
import com.esprit.dto.response.SpecialityResponse;
import com.esprit.repository.SpecialityRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Private Build)"
)
@Component
public class ClassMapperImpl implements ClassMapper {

    @Override
    public ClassEntity createClassRoomRequestToClassEntity(CreateClassRequest createClassRoomRequest, SpecialityRepository specialityRepository) {
        if ( createClassRoomRequest == null ) {
            return null;
        }

        ClassEntity classEntity = new ClassEntity();

        classEntity.setCode( createClassRoomRequest.getCode() );
        classEntity.setLabel( createClassRoomRequest.getLabel() );
        classEntity.setNbrStudents( createClassRoomRequest.getNbrStudents() );
        classEntity.setNbrGroups( createClassRoomRequest.getNbrGroups() );

        ClassMapper.after( createClassRoomRequest, classEntity, specialityRepository );

        return classEntity;
    }

    @Override
    public ClassResponse classEntityToClassResponse(ClassEntity classEntity) {
        if ( classEntity == null ) {
            return null;
        }

        ClassResponse classResponse = new ClassResponse();

        classResponse.setClassId( classEntity.getClassId() );
        classResponse.setCode( classEntity.getCode() );
        classResponse.setLabel( classEntity.getLabel() );
        classResponse.setNbrStudents( classEntity.getNbrStudents() );
        classResponse.setNbrGroups( classEntity.getNbrGroups() );
        classResponse.setSpeciality( specialityEntityToSpecialityResponse( classEntity.getSpeciality() ) );

        return classResponse;
    }

    @Override
    public List<ClassResponse> classEntitiesToClassResponse(List<ClassEntity> classEntities) {
        if ( classEntities == null ) {
            return null;
        }

        List<ClassResponse> list = new ArrayList<ClassResponse>( classEntities.size() );
        for ( ClassEntity classEntity : classEntities ) {
            list.add( classEntityToClassResponse( classEntity ) );
        }

        return list;
    }

    protected SpecialityResponse specialityEntityToSpecialityResponse(SpecialityEntity specialityEntity) {
        if ( specialityEntity == null ) {
            return null;
        }

        SpecialityResponse specialityResponse = new SpecialityResponse();

        specialityResponse.setSpecialityId( specialityEntity.getSpecialityId() );
        specialityResponse.setCode( specialityEntity.getCode() );
        specialityResponse.setLabel( specialityEntity.getLabel() );

        return specialityResponse;
    }
}
