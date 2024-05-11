package br.com.danieloliveira.foldermonitor.batch.config;

import java.io.IOException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import br.com.danieloliveira.foldermonitor.batch.fieldsetmapper.VisitorsFieldSetMapper;
import br.com.danieloliveira.foldermonitor.batch.processor.VisitorsItemProcessor;
import br.com.danieloliveira.foldermonitor.batch.writer.VisitorsItemWriter;
import br.com.danieloliveira.foldermonitor.core.domain.Visitors;
import br.com.danieloliveira.foldermonitor.core.dto.VisitorsDto;
import br.com.danieloliveira.foldermonitor.core.repositories.VisitorsRepository;
import br.com.danieloliveira.foldermonitor.infra.mapper.VisitorsMapper;
import lombok.val;

@Configuration
public class VisitorsBatchConfig {


    @Bean
    public Job importVistorsJob(final JobRepository jobRepository, final PlatformTransactionManager transactionManager, final VisitorsRepository visitorsRepository, final VisitorsMapper visitorsMapper) throws IOException {
        return new JobBuilder("importVisitorsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importVisitorsStep(jobRepository, transactionManager, visitorsRepository, visitorsMapper))
                .build();
    }

    @Bean
    public Step importVisitorsStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager, final VisitorsRepository visitorsRepository, final VisitorsMapper visitorsMapper) throws IOException {
        return new StepBuilder("importVisitorsStep", jobRepository)
                .<VisitorsDto, Visitors>chunk(100, transactionManager)
                .reader(flatFileItemReader(null))
                .processor(itemProcessor(visitorsMapper))
                .writer(visitorsItemWriter(visitorsRepository))
                .build();
    }

    @Bean
    public ItemProcessor<VisitorsDto, Visitors> itemProcessor(final VisitorsMapper visitorsMapper) {
        return new VisitorsItemProcessor(visitorsMapper);
    }

    @Bean
    public VisitorsItemWriter visitorsItemWriter(final VisitorsRepository visitorsRepository) {
        return new VisitorsItemWriter(visitorsRepository);
    }


    @Bean
    @StepScope
    public FlatFileItemReader<VisitorsDto> flatFileItemReader(@Value("#{jobParameters['inputFile']}") final String visitorsFile) throws IOException {
        val flatFileItemReader = new FlatFileItemReader<VisitorsDto>();
        flatFileItemReader.setName("VISITORS_READER");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(linMapper());
        flatFileItemReader.setStrict(false);
        flatFileItemReader.setResource(new FileSystemResource(visitorsFile));
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<VisitorsDto> linMapper() {
        val lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("visitorId", "firstName", "lastName", "emailAddress", "phoneNumber", "address", "visitDate");
        lineTokenizer.setStrict(false);
        val defaultLineMapper = new DefaultLineMapper<VisitorsDto>();
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(new VisitorsFieldSetMapper());
        return defaultLineMapper;

    }

}