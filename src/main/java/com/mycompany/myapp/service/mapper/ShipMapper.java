package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Country;
import com.mycompany.myapp.domain.Flag;
import com.mycompany.myapp.domain.Ship;
import com.mycompany.myapp.service.dto.CountryDTO;
import com.mycompany.myapp.service.dto.FlagDTO;
import com.mycompany.myapp.service.dto.ShipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ship} and its DTO {@link ShipDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipMapper extends EntityMapper<ShipDTO, Ship> {
    @Mapping(target = "ownerCountry", source = "ownerCountry", qualifiedByName = "countryId")
    @Mapping(target = "flag", source = "flag", qualifiedByName = "flagId")
    ShipDTO toDto(Ship s);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);

    @Named("flagId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FlagDTO toDtoFlagId(Flag flag);
}
