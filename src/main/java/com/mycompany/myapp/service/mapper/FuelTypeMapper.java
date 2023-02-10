package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.FuelType;
import com.mycompany.myapp.service.dto.FuelTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FuelType} and its DTO {@link FuelTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface FuelTypeMapper extends EntityMapper<FuelTypeDTO, FuelType> {}
