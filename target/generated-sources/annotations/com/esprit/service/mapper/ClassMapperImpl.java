package com.esprit.service.mapper;

import com.esprit.domain.ClassEntity;
import com.esprit.dto.ClassDTO;
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
    public ClassEntity classDTOToClassEntity(ClassDTO classDTO) {
        if ( classDTO == null ) {
            return null;
        }

        ClassEntity classEntity = new ClassEntity();

        classEntity.setClassId( classDTO.getClassId() );
        classEntity.setLabel( classDTO.getLabel() );
        classEntity.setNbrStudents( classDTO.getNbrStudents() );
        classEntity.setSpeciality( classDTO.getSpeciality() );
        classEntity.setCategory( classDTO.getCategory() );
        classEntity.setEmail( classDTO.getEmail() );

        return classEntity;
    }

    @Override
    public ClassDTO classEntityToClassDTO(ClassEntity classEntity) {
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

    @Override
    public List<ClassDTO> classEntitiesToClassDTO(List<ClassEntity> classEntities) {
        if ( classEntities == null ) {
            return null;
        }

        List<ClassDTO> list = new ArrayList<ClassDTO>( classEntities.size() );
        for ( ClassEntity classEntity : classEntities ) {
            list.add( classEntityToClassDTO( classEntity ) );
        }

        return list;
    }
}
