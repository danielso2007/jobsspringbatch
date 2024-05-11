package br.com.danieloliveira.batchprocessing.batch.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import br.com.danieloliveira.batchprocessing.core.dto.PersonDto;

@Configuration
public class PersonItemReaderConfig {
    @Bean
    public FlatFileItemReader<PersonDto> personReader() {
        return new FlatFileItemReaderBuilder<PersonDto>().name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv")).delimited().names("firstName", "lastName")
                .targetType(PersonDto.class).build();
    }
}
