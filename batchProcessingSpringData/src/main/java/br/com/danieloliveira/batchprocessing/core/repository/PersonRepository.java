package br.com.danieloliveira.batchprocessing.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.danieloliveira.batchprocessing.core.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
