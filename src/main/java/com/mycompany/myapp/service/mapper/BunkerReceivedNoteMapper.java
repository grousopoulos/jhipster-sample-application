package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.BunkerReceivedNote;
import com.mycompany.myapp.domain.Voyage;
import com.mycompany.myapp.service.dto.BunkerReceivedNoteDTO;
import com.mycompany.myapp.service.dto.VoyageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BunkerReceivedNote} and its DTO {@link BunkerReceivedNoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface BunkerReceivedNoteMapper extends EntityMapper<BunkerReceivedNoteDTO, BunkerReceivedNote> {
    @Mapping(target = "voyage", source = "voyage", qualifiedByName = "voyageId")
    BunkerReceivedNoteDTO toDto(BunkerReceivedNote s);

    @Named("voyageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VoyageDTO toDtoVoyageId(Voyage voyage);
}
