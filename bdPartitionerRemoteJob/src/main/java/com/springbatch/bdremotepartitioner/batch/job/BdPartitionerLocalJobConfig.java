package com.springbatch.bdremotepartitioner.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("localPartitioner")
@Configuration
public class BdPartitionerLocalJobConfig {

    private final JobRepository jobRepository;

    public BdPartitionerLocalJobConfig(final JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Bean
    public Job bdPartitionerLocalJob(@Qualifier("migrarPessoaManager") final Step migrarPessoaStep,
            @Qualifier("migrarDadosBancariosManager") final Step migrarDadosBancariosStep) {
        return new JobBuilder("simplePartitionerJob", jobRepository).start(migrarPessoaStep)
                .next(migrarDadosBancariosStep).incrementer(new RunIdIncrementer()).build();
    }
}
