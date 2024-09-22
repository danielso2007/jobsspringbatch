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
import com.springbatch.simplepartitionerarquivoslocaljob.code.dominio.Pessoa;

@Configuration
public class MigrarPessoaStepConfig {

    private final PlatformTransactionManager transactionManagerApp;
    private final Integer totalRegistros;
    private final Integer gridSize;

    public MigrarPessoaStepConfig(@Qualifier("transactionManagerApp") PlatformTransactionManager transactionManagerApp,
            @Value("${migracaodados.totalRegistros}") Integer totalRegistros,
            @Value("${migracaodados.gridSize}") Integer gridSize) {
        this.transactionManagerApp = transactionManagerApp;
        this.totalRegistros = totalRegistros;
        this.gridSize = gridSize;
    }

    @Bean
    public Step migrarPessoaManager(JobRepository jobRepository,
            @Qualifier("pessoaPartitioner") Partitioner pessoaPartitioner,
            @Qualifier("arquivoPessoaPartitionReader") ItemStreamReader<Pessoa> arquivoPessoaReader,
            JdbcBatchItemWriter<Pessoa> pessoaWriter,
            TaskExecutor taskExecutor) {
        return new StepBuilder("migrarPessoaStepArquivosPartitioner.manager", jobRepository)
                .partitioner("migrarPessoaStep.manager", pessoaPartitioner)
                .step(migrarPessoaStep(jobRepository, arquivoPessoaReader, pessoaWriter))
                .gridSize(gridSize)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Step migrarPessoaStep(JobRepository jobRepository, ItemReader<Pessoa> arquivoPessoaReader,
            JdbcBatchItemWriter<Pessoa> pessoaWriter) {
        return new StepBuilder("migrarPessoaStepPartitioner", jobRepository)
                .<Pessoa, Pessoa>chunk(totalRegistros / gridSize, transactionManagerApp)
                .reader(arquivoPessoaReader)
                .writer(pessoaWriter)
                .transactionManager(transactionManagerApp)
                .build();
    }
}
