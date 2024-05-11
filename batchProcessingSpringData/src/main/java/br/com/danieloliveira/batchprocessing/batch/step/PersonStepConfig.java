package br.com.danieloliveira.batchprocessing.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import br.com.danieloliveira.batchprocessing.batch.processor.PersonItemProcessorConfig;
import br.com.danieloliveira.batchprocessing.core.domain.Person;
import br.com.danieloliveira.batchprocessing.core.dto.PersonDto;

@Configuration
public class PersonStepConfig {
    @Bean
    public Step personStep(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
            FlatFileItemReader<PersonDto> personReader, PersonItemProcessorConfig personProcessor,
            ItemWriter<Person> personWriter) {
        return new StepBuilder("personStep", jobRepository).<PersonDto, Person>chunk(100, transactionManager)
                .reader(personReader).processor(personProcessor).writer(personWriter).build();
    }
}
