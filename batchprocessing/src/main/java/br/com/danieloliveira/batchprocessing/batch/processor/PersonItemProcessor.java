package br.com.danieloliveira.batchprocessing.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import br.com.danieloliveira.batchprocessing.domain.Person;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(@SuppressWarnings("null") final Person person) {
        final String firstName = person.firstName().toUpperCase();
        final String lastName = person.lastName().toUpperCase();
        final Person transformedPerson = new Person(firstName, lastName);
        log.info("Converting (" + person + ") into (" + transformedPerson + ")");
        return transformedPerson;
    }
}
