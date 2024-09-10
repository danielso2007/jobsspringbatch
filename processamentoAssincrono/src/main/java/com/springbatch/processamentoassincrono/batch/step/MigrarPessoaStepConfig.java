package com.springbatch.processamentoassincrono.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import com.springbatch.processamentoassincrono.code.dominio.Pessoa;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MigrarPessoaStepConfig {

    @Qualifier("transactionManagerApp")
    private final PlatformTransactionManager transactionManagerApp;

    @Bean
    public Step migrarPessoaStep(JobRepository jobRepository,
            ItemReader<Pessoa> arquivoPessoaReader,
            AsyncItemWriter<Pessoa> pessoaWriter,
            AsyncItemProcessor<Pessoa, Pessoa> pessoaProcessor) {
        return new StepBuilder("AsyncMigrarPessoaStep", jobRepository)
                // Para mostrar o uso de memória
                .<Pessoa, Pessoa>chunk(1000, transactionManagerApp)
                .reader(arquivoPessoaReader)
                // Para mostrar o uso de CPU
                .processor((ItemProcessor) pessoaProcessor)
                .writer(pessoaWriter)
                .transactionManager(transactionManagerApp)
                .build();
    }
}
