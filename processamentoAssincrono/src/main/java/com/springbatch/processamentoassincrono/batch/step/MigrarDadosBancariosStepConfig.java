package com.springbatch.processamentoassincrono.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import com.springbatch.processamentoassincrono.code.dominio.DadosBancarios;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MigrarDadosBancariosStepConfig {

    @Qualifier("transactionManagerApp")
    private final PlatformTransactionManager transactionManagerApp;

    @Bean
    public Step migrarDadosBancariosStep(JobRepository jobRepository,
            ItemReader<DadosBancarios> arquivoDadosBancariosReader,
            ItemWriter<DadosBancarios> bancoDadosBancariosWriter) {
        return new StepBuilder("migrarDadosBancariosStep", jobRepository)
                .<DadosBancarios, DadosBancarios>chunk(1000, transactionManagerApp)
                .reader(arquivoDadosBancariosReader)
                .writer(bancoDadosBancariosWriter)
                .build();
    }
}
