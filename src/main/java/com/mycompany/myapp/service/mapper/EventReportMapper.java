package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.EventReport;
import com.mycompany.myapp.domain.Voyage;
import com.mycompany.myapp.service.dto.EventReportDTO;
import com.mycompany.myapp.service.dto.VoyageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventReport} and its DTO {@link EventReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventReportMapper extends EntityMapper<EventReportDTO, EventReport> {
    @Mapping(target = "voyage", source = "voyage", qualifiedByName = "voyageId")
    EventReportDTO toDto(EventReport s);

    @Named("voyageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VoyageDTO toDtoVoyageId(Voyage voyage);
}
