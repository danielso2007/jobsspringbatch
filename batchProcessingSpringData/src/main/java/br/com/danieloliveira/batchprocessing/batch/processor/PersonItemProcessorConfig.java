package br.com.danieloliveira.batchprocessing.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import br.com.danieloliveira.batchprocessing.core.domain.Person;
import br.com.danieloliveira.batchprocessing.core.dto.PersonDto;
import br.com.danieloliveira.batchprocessing.infra.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PersonItemProcessorConfig implements ItemProcessor<PersonDto, Person> {
    private final PersonMapper personMapper;

    @Override
    public Person process(@SuppressWarnings("null") final PersonDto personDto) {
        return personMapper.toEntity(personDto);
    }

    @Bean
    public PersonItemProcessorConfig personProcessor(PersonMapper personMapper) {
        return new PersonItemProcessorConfig(personMapper);
    }
}
