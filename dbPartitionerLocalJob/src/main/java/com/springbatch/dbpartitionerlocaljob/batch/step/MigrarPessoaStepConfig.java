package com.springbatch.dbpartitionerlocaljob.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import com.springbatch.dbpartitionerlocaljob.code.dominio.Pessoa;

@Configuration
public class MigrarPessoaStepConfig {

  private PlatformTransactionManager transactionManagerApp;

  public MigrarPessoaStepConfig(
      @Qualifier("transactionManagerApp") PlatformTransactionManager transactionManagerApp) {
    this.transactionManagerApp = transactionManagerApp;
  }

  @Bean
  public Step migrarPessoaManager(JobRepository jobRepository,
      @Qualifier("pessoaPartitioner") Partitioner pessoaPartitioner,
      ItemReader<Pessoa> arquivoPessoaReader,
      ItemWriter<Pessoa> pessoaWriter,
      TaskExecutor taskExecutor) {
    return new StepBuilder("migrarPessoaStepManager", jobRepository)
        .partitioner("migrarPessoaStep.manager", pessoaPartitioner)
        .step(migrarPessoaStep(jobRepository, arquivoPessoaReader, pessoaWriter))
        .gridSize(10)
        .taskExecutor(taskExecutor)
        .build();
  }

  private Step migrarPessoaStep(JobRepository jobRepository,
      ItemReader<Pessoa> pessoaReader,
      ItemWriter<Pessoa> pessoaWriter) {
    return new StepBuilder("migrarPessoaStep", jobRepository)
        .<Pessoa, Pessoa>chunk(2000, transactionManagerApp)
        .reader(pessoaReader)
        .writer(pessoaWriter)
        .transactionManager(transactionManagerApp)
        .build();
  }
}
