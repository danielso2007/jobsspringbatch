package com.springbatch.simplepartitionerarquivoslocaljob.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import com.springbatch.simplepartitionerarquivoslocaljob.code.dominio.DadosBancarios;

@Configuration
public class DividirArquivoDadosBancariosStepConfig {

    private final PlatformTransactionManager transactionManagerApp;

    public DividirArquivoDadosBancariosStepConfig(PlatformTransactionManager transactionManagerApp) {
        this.transactionManagerApp = transactionManagerApp;
    }

    @Bean
    public Step dividirArquivoDadosBancariosStep(final JobRepository jobRepository,
            @Qualifier("dadosBancariosReaderFlatMultiResources") final FlatFileItemReader<DadosBancarios> dadosBancariosReaderFlatMultiResources,
            final MultiResourceItemWriter<DadosBancarios> multiResourceDadosBancariosWriter) {

        return new StepBuilder("dividirArquivoDadosBancariosStep", jobRepository)
                .<DadosBancarios, DadosBancarios>chunk(2000, transactionManagerApp)
                .reader(dadosBancariosReaderFlatMultiResources)
                .writer(multiResourceDadosBancariosWriter)
                .stream(multiResourceDadosBancariosWriter)
                .transactionManager(transactionManagerApp)
                .build();
    }

}
