package com.springbatch.dbpartitionerlocaljob.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbPartitionerLocalJobConfig {

  @Bean
  public Job bdPartitionerLocalJob(final JobRepository jobRepository,
      @Qualifier("migrarPessoaManager") final Step migrarPessoaStep,
      @Qualifier("migrarDadosBancariosManager") final Step migrarDadosBancariosStep) {
    return new JobBuilder("bdPartitionerLocalJob", jobRepository)
        .start(migrarPessoaStep)
        .next(migrarDadosBancariosStep)
        .incrementer(new RunIdIncrementer()).build();
  }
}
