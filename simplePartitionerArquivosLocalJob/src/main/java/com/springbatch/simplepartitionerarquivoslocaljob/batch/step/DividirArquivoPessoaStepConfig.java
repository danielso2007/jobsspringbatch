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
import com.springbatch.simplepartitionerarquivoslocaljob.code.dominio.Pessoa;

@Configuration
public class DividirArquivoPessoaStepConfig {

    private final PlatformTransactionManager transactionManagerApp;

    public DividirArquivoPessoaStepConfig(PlatformTransactionManager transactionManagerApp) {
        this.transactionManagerApp = transactionManagerApp;
    }

    @Bean
    public Step dividirArquivoPessoaStep(final JobRepository jobRepository,
            @Qualifier("arquivoPessoaReaderMultiResources") final FlatFileItemReader<Pessoa> arquivoPessoaReaderMultiResources,
            final MultiResourceItemWriter<Pessoa> multiResourcePessoaWriter) {

        return new StepBuilder("dividirArquivoPessoaStep", jobRepository)
                .<Pessoa, Pessoa>chunk(2000, transactionManagerApp)
                .reader(arquivoPessoaReaderMultiResources)
                .writer(multiResourcePessoaWriter)
                .stream(multiResourcePessoaWriter)
                .transactionManager(transactionManagerApp)
                .build();
    }

}
