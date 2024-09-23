package com.springbatch.dbpartitionerlocaljob.batch.step.impl;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import com.springbatch.dbpartitionerlocaljob.batch.step.StepConfig;
import com.springbatch.dbpartitionerlocaljob.code.dominio.Pessoa;

@Configuration
public class MigrarPessoaStepConfig extends StepConfig<Pessoa> {

    public MigrarPessoaStepConfig(final JobRepository jobRepository,
            final @Qualifier("transactionManagerApp") PlatformTransactionManager transactionManagerApp) {
        super("migrarPessoa", jobRepository, transactionManagerApp, null, null);
    }

    @Bean
    public Step migrarPessoaManager(
            @Qualifier("pessoaPartitioner") Partitioner partitioner,
            @Qualifier("pessoaReader") ItemReader<Pessoa> reader,
            @Qualifier("pessoaWriter") ItemWriter<Pessoa> writer,
            TaskExecutor taskExecutor) {
        return manager(partitioner, reader, writer, taskExecutor);
    }

}
