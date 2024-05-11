package br.com.danieloliveira.batchprocessing.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import br.com.danieloliveira.batchprocessing.domain.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(@SuppressWarnings("null") final Person person) {
        final String firstName = person.firstName().toUpperCase();
        final String lastName = person.lastName().toUpperCase();
        return new Person(firstName, lastName);
    }
}
