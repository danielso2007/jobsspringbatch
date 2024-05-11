package br.com.danieloliveira.batchprocessing.batch.writer;

import java.beans.JavaBean;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;
import br.com.danieloliveira.batchprocessing.core.domain.Person;
import br.com.danieloliveira.batchprocessing.core.repository.PersonRepository;
import lombok.RequiredArgsConstructor;

@JavaBean
@RequiredArgsConstructor
public class PersonItemWriter implements ItemWriter<Person> {
    private final PersonRepository personRepository;

    @Override
    public void write(@NonNull Chunk<? extends Person> chunk) throws Exception {
        personRepository.saveAll(chunk.getItems());
    }
}
