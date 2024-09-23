package com.springbatch.bdremotepartitioner.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import com.springbatch.bdremotepartitioner.core.dominio.DadosBancarios;

@Profile("localPartitioner")
@Configuration
public class MigrarDadosBancariosStepConfig {

    private final PlatformTransactionManager transactionManagerApp;
    private final JobRepository jobRepository;

    public MigrarDadosBancariosStepConfig(final JobRepository jobRepository,
            @Qualifier("transactionManagerApp") final PlatformTransactionManager transactionManagerApp) {
        this.jobRepository = jobRepository;
        this.transactionManagerApp = transactionManagerApp;
    }


    @Bean
    public Step migrarDadosBancariosManager(
            JdbcPagingItemReader<DadosBancarios> dadosBancariosReader,
            JdbcBatchItemWriter<DadosBancarios> dadosBancariosWriter,
            @Qualifier("dadosBancariosPartitioner") Partitioner partitioner,
            TaskExecutor taskExecutor) {
        return new StepBuilder("migrarDadosBancariosManager", jobRepository)
                .partitioner("migrarDadosBancarios.manager", partitioner).taskExecutor(taskExecutor)
                .gridSize(10)
                .step(migrarDadosBancariosStep(dadosBancariosReader, dadosBancariosWriter)).build();
    }

    private Step migrarDadosBancariosStep(JdbcPagingItemReader<DadosBancarios> dadosBancariosReader,
            JdbcBatchItemWriter<DadosBancarios> dadosBancariosWriter) {
        return new StepBuilder("migrarDadosBancariosStep", jobRepository)
                .<DadosBancarios, DadosBancarios>chunk(2000, transactionManagerApp)
                .reader(dadosBancariosReader).writer(dadosBancariosWriter)
                .transactionManager(transactionManagerApp).build();
    }
}
