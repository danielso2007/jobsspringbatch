package br.com.danieloliveira.foldermonitor.infra.mapper;

import org.mapstruct.Mapper;
import br.com.danieloliveira.foldermonitor.core.domain.Visitors;
import br.com.danieloliveira.foldermonitor.core.dto.VisitorsDto;

@Mapper(componentModel = "spring")
public interface VisitorsMapper {
    Visitors toVisitors(VisitorsDto visitorsDto);
}