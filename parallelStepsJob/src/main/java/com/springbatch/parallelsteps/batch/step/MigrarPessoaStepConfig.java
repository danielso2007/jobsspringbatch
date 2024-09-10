package com.springbatch.parallelsteps.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import com.springbatch.parallelsteps.code.dominio.Pessoa;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MigrarPessoaStepConfig {
    @Qualifier("transactionManagerApp")
    private final PlatformTransactionManager transactionManagerApp;

    @Bean
    public Step migrarPessoaStep(JobRepository jobRepository, ItemReader<Pessoa> arquivoPessoaReader,
            ItemWriter<Pessoa> pessoaWriter, ItemProcessor<Pessoa, Pessoa> pessoaProcessor) {
        return new StepBuilder("migrarPessoaStepParalelo", jobRepository)
                // Para mostrar o uso de memória
                .<Pessoa, Pessoa>chunk(10000, transactionManagerApp).reader(arquivoPessoaReader)
                // Para mostrar o uso de CPU
                // .processor(pessoaProcessor)
                .writer(pessoaWriter).transactionManager(transactionManagerApp).build();
    }
}
