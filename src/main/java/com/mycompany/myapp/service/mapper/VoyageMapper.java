package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Voyage;
import com.mycompany.myapp.service.dto.VoyageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Voyage} and its DTO {@link VoyageDTO}.
 */
@Mapper(componentModel = "spring")
public interface VoyageMapper extends EntityMapper<VoyageDTO, Voyage> {}
