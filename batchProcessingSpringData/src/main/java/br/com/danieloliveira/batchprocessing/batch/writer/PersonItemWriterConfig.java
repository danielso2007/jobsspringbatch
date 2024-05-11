package br.com.danieloliveira.batchprocessing.batch.writer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import br.com.danieloliveira.batchprocessing.core.repository.PersonRepository;

@Configuration
public class PersonItemWriterConfig {
    @Bean
    public PersonItemWriter personWriter(final PersonRepository personRepository) {
        return new PersonItemWriter(personRepository);
    }
}
