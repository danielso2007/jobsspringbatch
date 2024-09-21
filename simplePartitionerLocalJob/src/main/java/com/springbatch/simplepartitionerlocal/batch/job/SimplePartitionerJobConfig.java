package com.springbatch.simplepartitionerlocal.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimplePartitionerJobConfig {
    @Bean
    public Job migracaoDadosJob(final JobRepository jobRepository,
            @Qualifier("migrarPessoaManager") final Step migrarPessoaManager,
            @Qualifier("migrarDadosBancariosManager") final Step migrarDadosBancariosManager) {
        return new JobBuilder("simplePartitionerJob", jobRepository)
                .start(migrarPessoaManager)
                .next(migrarDadosBancariosManager)
                .incrementer(new RunIdIncrementer())
                .build();
    }

}