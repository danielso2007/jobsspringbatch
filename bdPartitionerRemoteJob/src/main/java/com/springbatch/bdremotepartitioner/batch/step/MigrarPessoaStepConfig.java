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
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import com.springbatch.bdremotepartitioner.core.dominio.Pessoa;

@Configuration
public class MigrarPessoaStepConfig {

    private final PlatformTransactionManager transactionManagerApp;
    private final JobRepository jobRepository;

    public MigrarPessoaStepConfig(final JobRepository jobRepository,
            @Qualifier("transactionManagerApp") final PlatformTransactionManager transactionManagerApp) {
        this.jobRepository = jobRepository;
        this.transactionManagerApp = transactionManagerApp;
    }

    @Bean
    public Step migrarPessoaManager(JdbcPagingItemReader<Pessoa> pessoaReader,
            JdbcBatchItemWriter<Pessoa> pessoaWriter,
            @Qualifier("pessoaPartitioner") Partitioner partitioner, TaskExecutor taskExecutor) {
        return new StepBuilder("migrarPessoaManager", jobRepository)
                .partitioner("migrarPessoa.manager", partitioner).taskExecutor(taskExecutor)
                .gridSize(10).step(migrarPessoaStep(pessoaReader, pessoaWriter)).build();
    }

    private Step migrarPessoaStep(JdbcPagingItemReader<Pessoa> pessoaReader,
            JdbcBatchItemWriter<Pessoa> pessoaWriter) {
        return new StepBuilder("migrarPessoaStep", jobRepository)
                .<Pessoa, Pessoa>chunk(2000, transactionManagerApp).reader(pessoaReader)
                .writer(pessoaWriter).transactionManager(transactionManagerApp).build();
    }
}
