package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Port;
import com.mycompany.myapp.service.dto.PortDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Port} and its DTO {@link PortDTO}.
 */
@Mapper(componentModel = "spring")
public interface PortMapper extends EntityMapper<PortDTO, Port> {}
