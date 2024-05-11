package br.com.danieloliveira.batchprocessing.infra.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.com.danieloliveira.batchprocessing.core.domain.Person;
import br.com.danieloliveira.batchprocessing.core.dto.PersonDto;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "creationDate")
    @Mapping(ignore = true, target = "updateDate")
    Person toEntity(PersonDto dto);
}
