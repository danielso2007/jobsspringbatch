package com.springbatch.simplepartitionerarquivoslocaljob.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import com.springbatch.simplepartitionerarquivoslocaljob.code.dominio.DadosBancarios;

@Configuration
public class MigrarDadosBancariosStepConfig {

    private final PlatformTransactionManager transactionManagerApp;
    private final Integer totalRegistros;
    private final Integer gridSize;

    public MigrarDadosBancariosStepConfig(
            @Qualifier("transactionManagerApp") PlatformTransactionManager transactionManagerApp,
            @Value("${migracaodados.totalRegistros}") Integer totalRegistros,
            @Value("${migracaodados.gridSize}") Integer gridSize) {
        this.transactionManagerApp = transactionManagerApp;
        this.totalRegistros = totalRegistros;
        this.gridSize = gridSize;
    }

    @Bean
    public Step migrarDadosBancariosManager(JobRepository jobRepository,
            @Qualifier("dadosBancariosPartitioner") Partitioner dadosBancariosPartitioner,
            @Qualifier("arquivoDadosBancariosPartitionReader") ItemStreamReader<DadosBancarios> arquivoDadosBancariosReader,
            JdbcBatchItemWriter<DadosBancarios> dadosBancariosWriter,
            TaskExecutor taskExecutor) {
        return new StepBuilder("migrarDadosBancariosStepArquivosPartitioner.manager", jobRepository)
                .partitioner("migrarDadosBancariosStep.manager", dadosBancariosPartitioner)
                .step(migrarDadosBancariosStep(jobRepository, arquivoDadosBancariosReader, dadosBancariosWriter))
                .gridSize(gridSize)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Step migrarDadosBancariosStep(JobRepository jobRepository,
            ItemReader<DadosBancarios> arquivoDadosBancariosReader,
            JdbcBatchItemWriter<DadosBancarios> bancoDadosBancariosWriter) {
        return new StepBuilder("migrarDadosBancariosStepPartitioner", jobRepository)
                .<DadosBancarios, DadosBancarios>chunk(totalRegistros / gridSize, transactionManagerApp)
                .reader(arquivoDadosBancariosReader)
                .writer(bancoDadosBancariosWriter).build();
    }
}
