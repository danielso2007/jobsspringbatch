package com.springbatch.simplepartitionerlocal.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import com.springbatch.simplepartitionerlocal.code.dominio.Pessoa;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MigrarPessoaStepConfig {

    @Qualifier("transactionManagerApp")
    private final PlatformTransactionManager transactionManagerApp;

    @Value("${migracaodados.totalRegistros}")
    private Integer totalRegistros;

    @Value("${migracaodados.gridSize}")
    private Integer gridSize;

    @Bean
    public Step migrarPessoaManager(JobRepository jobRepository,
            ItemReader<Pessoa> arquivoPessoaReader,
            ItemWriter<Pessoa> pessoaWriter,
            Partitioner partitioner,
            TaskExecutor taskExecutor) {
        return new StepBuilder("migrarPessoaStepPartitioner.manager", jobRepository)
                .partitioner("migrarPessoaStepPartitioner", partitioner)
                .step(migrarPessoaStep(jobRepository, arquivoPessoaReader, pessoaWriter))
                .gridSize(gridSize)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Step migrarPessoaStep(JobRepository jobRepository, ItemReader<Pessoa> arquivoPessoaReader,
            ItemWriter<Pessoa> pessoaWriter) {
        return new StepBuilder("migrarPessoaStepPartitioner", jobRepository)
                .<Pessoa, Pessoa>chunk(totalRegistros / gridSize, transactionManagerApp)
                .reader(arquivoPessoaReader)
                .writer(pessoaWriter)
                .transactionManager(transactionManagerApp)
                .build();
    }
}
