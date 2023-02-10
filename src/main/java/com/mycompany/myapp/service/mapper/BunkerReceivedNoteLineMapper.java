package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.BunkerReceivedNoteLine;
import com.mycompany.myapp.service.dto.BunkerReceivedNoteLineDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BunkerReceivedNoteLine} and its DTO {@link BunkerReceivedNoteLineDTO}.
 */
@Mapper(componentModel = "spring")
public interface BunkerReceivedNoteLineMapper extends EntityMapper<BunkerReceivedNoteLineDTO, BunkerReceivedNoteLine> {}
