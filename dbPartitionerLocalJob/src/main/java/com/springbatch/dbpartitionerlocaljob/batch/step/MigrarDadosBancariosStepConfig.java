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
import com.springbatch.dbpartitionerlocaljob.code.dominio.DadosBancarios;

@Configuration
public class MigrarDadosBancariosStepConfig {

  private PlatformTransactionManager transactionManagerApp;

  public MigrarDadosBancariosStepConfig(
      @Qualifier("transactionManagerApp") PlatformTransactionManager transactionManagerApp) {
    this.transactionManagerApp = transactionManagerApp;
  }

  @Bean
  public Step migrarDadosBancariosManager(JobRepository jobRepository,
      @Qualifier("dadosBancariosPartitioner") Partitioner pessoaPartitioner,
      ItemReader<DadosBancarios> arquivoPessoaReader,
      ItemWriter<DadosBancarios> pessoaWriter,
      TaskExecutor taskExecutor) {
    return new StepBuilder("migrarDadosBancariosManager", jobRepository)
        .partitioner("migrarDadosBancariosStep.manager", pessoaPartitioner)
        .step(migrarDadosBancariosStep(jobRepository, arquivoPessoaReader, pessoaWriter))
        .gridSize(10)
        .taskExecutor(taskExecutor)
        .build();
  }

  private Step migrarDadosBancariosStep(JobRepository jobRepository,
      ItemReader<DadosBancarios> dadosBancariosReader,
      ItemWriter<DadosBancarios> dadosBancariosWriter) {
    return new StepBuilder("migrarDadosBancariosStep", jobRepository)
        .<DadosBancarios, DadosBancarios>chunk(2000, transactionManagerApp)
        .reader(dadosBancariosReader)
        .writer(dadosBancariosWriter)
        .transactionManager(transactionManagerApp)
        .build();
  }
}
