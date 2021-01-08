package com.esprit.service.mapper;

import com.esprit.domain.SiteEntity;
import com.esprit.dto.request.CreateSiteRequest;
import com.esprit.dto.response.SiteResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 1.3.1100.v20200828-0941, environment: Java 15 (Oracle Corporation)"
)
@Component
public class SiteMapperImpl implements SiteMapper {

    @Override
    public SiteEntity createSiteRequestToSiteEntity(CreateSiteRequest createSiteRequest) {
        if ( createSiteRequest == null ) {
            return null;
        }

        SiteEntity siteEntity = new SiteEntity();

        siteEntity.setCode( createSiteRequest.getCode() );
        siteEntity.setLabel( createSiteRequest.getLabel() );

        return siteEntity;
    }

    @Override
    public SiteResponse siteEntitToSiteResponse(SiteEntity siteEntity) {
        if ( siteEntity == null ) {
            return null;
        }

        SiteResponse siteResponse = new SiteResponse();

        siteResponse.setCode( siteEntity.getCode() );
        siteResponse.setLabel( siteEntity.getLabel() );
        siteResponse.setSiteId( siteEntity.getSiteId() );

        return siteResponse;
    }

    @Override
    public List<SiteResponse> siteEntitiesToSiteResponse(List<SiteEntity> siteEntities) {
        if ( siteEntities == null ) {
            return null;
        }

        List<SiteResponse> list = new ArrayList<SiteResponse>( siteEntities.size() );
        for ( SiteEntity siteEntity : siteEntities ) {
            list.add( siteEntitToSiteResponse( siteEntity ) );
        }

        return list;
    }
}
