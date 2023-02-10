package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.EventReportLine;
import com.mycompany.myapp.domain.FuelType;
import com.mycompany.myapp.service.dto.EventReportLineDTO;
import com.mycompany.myapp.service.dto.FuelTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventReportLine} and its DTO {@link EventReportLineDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventReportLineMapper extends EntityMapper<EventReportLineDTO, EventReportLine> {
    @Mapping(target = "fuelType", source = "fuelType", qualifiedByName = "fuelTypeId")
    EventReportLineDTO toDto(EventReportLine s);

    @Named("fuelTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FuelTypeDTO toDtoFuelTypeId(FuelType fuelType);
}
