package com.esprit.service.mapper;

import com.esprit.domain.ClassEntity;
import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.request.CreateClassRequest;
import com.esprit.dto.request.UpdateClassRequest;
import com.esprit.dto.response.ClassResponse;
import com.esprit.dto.response.SpecialityResponse;
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
public class ClassMapperImpl implements ClassMapper {

    @Override
    public ClassEntity createClassRequestRequestToClassEntity(CreateClassRequest createClassRequest, SpecialityRepository specialityRepository) {
        if ( createClassRequest == null ) {
            return null;
        }

        ClassEntity classEntity = new ClassEntity();

        classEntity.setCode( createClassRequest.getCode() );
        classEntity.setLabel( createClassRequest.getLabel() );
        classEntity.setNbrGroups( createClassRequest.getNbrGroups() );
        classEntity.setNbrStudents( createClassRequest.getNbrStudents() );

        ClassMapper.after( createClassRequest, classEntity, specialityRepository );

        return classEntity;
    }

    @Override
    public ClassEntity updateClassRequestRequestToClassEntity(UpdateClassRequest updateClassRequest, SpecialityRepository specialityRepository) {
        if ( updateClassRequest == null ) {
            return null;
        }

        ClassEntity classEntity = new ClassEntity();

        classEntity.setClassId( updateClassRequest.getClassId() );
        classEntity.setLabel( updateClassRequest.getLabel() );
        classEntity.setNbrGroups( updateClassRequest.getNbrGroups() );
        classEntity.setNbrStudents( updateClassRequest.getNbrStudents() );

        ClassMapper.after( updateClassRequest, classEntity, specialityRepository );

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
        classResponse.setCreatedDate( classEntity.getCreatedDate() );
        classResponse.setLabel( classEntity.getLabel() );
        classResponse.setNbrGroups( classEntity.getNbrGroups() );
        classResponse.setNbrStudents( classEntity.getNbrStudents() );
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

        specialityResponse.setCode( specialityEntity.getCode() );
        specialityResponse.setLabel( specialityEntity.getLabel() );
        specialityResponse.setSpecialityId( specialityEntity.getSpecialityId() );

        return specialityResponse;
    }
}
