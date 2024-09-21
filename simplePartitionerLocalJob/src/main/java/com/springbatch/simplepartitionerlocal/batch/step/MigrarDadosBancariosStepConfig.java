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
import com.springbatch.simplepartitionerlocal.code.dominio.DadosBancarios;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MigrarDadosBancariosStepConfig {
    @Qualifier("transactionManagerApp")
    private final PlatformTransactionManager transactionManagerApp;

    @Value("${migracaodados.totalRegistros}")
    private Integer totalRegistros;

    @Value("${migracaodados.gridSize}")
    private Integer gridSize;

    @Bean
    public Step migrarDadosBancariosManager(JobRepository jobRepository,
            ItemReader<DadosBancarios> arquivoPessoaReader,
            ItemWriter<DadosBancarios> pessoaWriter,
            Partitioner partitioner,
            TaskExecutor taskExecutor) {
        return new StepBuilder("migrarDadosBancariosStepPartitioner.manager", jobRepository)
                .partitioner("migrarDadosBancariosStepPartitioner", partitioner)
                .step(migrarDadosBancariosStep(jobRepository, arquivoPessoaReader, pessoaWriter))
                .gridSize(gridSize)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Step migrarDadosBancariosStep(JobRepository jobRepository,
            ItemReader<DadosBancarios> arquivoDadosBancariosReader,
            ItemWriter<DadosBancarios> bancoDadosBancariosWriter) {
        return new StepBuilder("migrarDadosBancariosStepPartitioner", jobRepository)
                .<DadosBancarios, DadosBancarios>chunk(totalRegistros / gridSize, transactionManagerApp)
                .reader(arquivoDadosBancariosReader)
                .writer(bancoDadosBancariosWriter).build();
    }
}
