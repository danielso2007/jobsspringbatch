package com.springbatch.simplepartitionerlocal.batch.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import com.springbatch.simplepartitionerlocal.code.dominio.DadosBancarios;
import com.springbatch.simplepartitionerlocal.infra.config.ArquivoPartitionerConfig;

@Configuration
public class ArquivoDadosBancariosReaderConfig {

    private ArquivoPartitionerConfig partitionerConfig;


    public ArquivoDadosBancariosReaderConfig(ArquivoPartitionerConfig partitionerConfig) {
        this.partitionerConfig = partitionerConfig;
    }


    @StepScope
    @Bean
    public CustomArquivoReader<DadosBancarios> arquivoPessoaReader(@Value("${stepExecutionContext['particao']}") Integer particao) {
        return new CustomArquivoReader<>(dadosBancariosReaderFlat(particao), partitionerConfig.getItensLimit());
    }

    public FlatFileItemReader<DadosBancarios> dadosBancariosReaderFlat(int currentItemCount) {
        return new FlatFileItemReaderBuilder<DadosBancarios>().name("dadosBancariosReader")
                .resource(new FileSystemResource("files/dados_bancarios.csv"))
                .delimited()
                .names("pessoaId", "agencia", "conta", "banco", "id")
                .addComment("--")
                .currentItemCount(currentItemCount)
                .targetType(DadosBancarios.class)
                .build();
    }
}
