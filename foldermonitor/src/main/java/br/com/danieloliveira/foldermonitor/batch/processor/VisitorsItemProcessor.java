package br.com.danieloliveira.foldermonitor.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import br.com.danieloliveira.foldermonitor.core.domain.Visitors;
import br.com.danieloliveira.foldermonitor.core.dto.VisitorsDto;
import br.com.danieloliveira.foldermonitor.infra.mapper.VisitorsMapper;

@Component
public class VisitorsItemProcessor implements ItemProcessor<VisitorsDto, Visitors> {
    private final VisitorsMapper visitorsMapper;

    public VisitorsItemProcessor(VisitorsMapper visitorsMapper) {
        this.visitorsMapper = visitorsMapper;
    }

    @SuppressWarnings("null")
    @Override
    public Visitors process(final VisitorsDto visitorsDto) {
        return visitorsMapper.toVisitors(visitorsDto);
    }
}