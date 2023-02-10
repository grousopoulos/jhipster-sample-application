package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Flag;
import com.mycompany.myapp.service.dto.FlagDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Flag} and its DTO {@link FlagDTO}.
 */
@Mapper(componentModel = "spring")
public interface FlagMapper extends EntityMapper<FlagDTO, Flag> {}
