package br.com.danieloliveira.foldermonitor.batch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import br.com.danieloliveira.foldermonitor.core.domain.Visitors;
import br.com.danieloliveira.foldermonitor.core.repositories.VisitorsRepository;

@Component
public class VisitorsItemWriter implements ItemWriter<Visitors> {
    private final VisitorsRepository visitorsRepository;

    public VisitorsItemWriter(VisitorsRepository visitorsRepository) {
        this.visitorsRepository = visitorsRepository;
    }

    @SuppressWarnings("null")
    @Override
    public void write(Chunk<? extends Visitors> visitors) {
        visitors.getItems().forEach(visitorsRepository::save);
    }
}
