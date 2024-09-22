package com.springbatch.dbpartitionerlocaljob.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
  public Step migrarDadosBancariosStep(JobRepository jobRepository,
      ItemStreamReader<DadosBancarios> dadosBancariosReader,
      JdbcBatchItemWriter<DadosBancarios> dadosBancariosWriter) {
    return new StepBuilder("migrarDadosBancariosStep", jobRepository)
        .<DadosBancarios, DadosBancarios>chunk(20000, transactionManagerApp)
        .reader(dadosBancariosReader)
        .writer(dadosBancariosWriter)
        .transactionManager(transactionManagerApp)
        .stream(dadosBancariosReader)
        .build();
  }
}
