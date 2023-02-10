package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ClasificationSociety;
import com.mycompany.myapp.service.dto.ClasificationSocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClasificationSociety} and its DTO {@link ClasificationSocietyDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClasificationSocietyMapper extends EntityMapper<ClasificationSocietyDTO, ClasificationSociety> {}
