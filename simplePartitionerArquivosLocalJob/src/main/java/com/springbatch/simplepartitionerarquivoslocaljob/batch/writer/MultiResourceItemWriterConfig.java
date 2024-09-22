package com.springbatch.simplepartitionerarquivoslocaljob.batch.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import com.springbatch.simplepartitionerarquivoslocaljob.code.dominio.DadosBancarios;
import com.springbatch.simplepartitionerarquivoslocaljob.code.dominio.Pessoa;

@Configuration
public class MultiResourceItemWriterConfig {

    @Bean
    public MultiResourceItemWriter<Pessoa> multiResourcePessoaWriter() {
        return new MultiResourceItemWriterBuilder<Pessoa>().name("multiResourcePessoaWriter").delegate(pessoaWriter())
                .resource(new FileSystemResource("files/pessoa-tmp")).itemCountLimitPerResource(2000).build();
    }

    private FlatFileItemWriter<Pessoa> pessoaWriter() {
        return new FlatFileItemWriterBuilder<Pessoa>().name("pessoaWriter").delimited()
                .names("nome", "email", "dataNascimentoStr", "idade", "id").build();
    }

    @Bean
    public MultiResourceItemWriter<DadosBancarios> multiResourceDadosBancariosWriter() {
        return new MultiResourceItemWriterBuilder<DadosBancarios>().name("multiResourcePessoaWriter")
                .delegate(dadosBancariosWriter())
                .resource(new FileSystemResource("files/dados_bancarios-tmp")).itemCountLimitPerResource(2000).build();
    }

    private FlatFileItemWriter<DadosBancarios> dadosBancariosWriter() {
        return new FlatFileItemWriterBuilder<DadosBancarios>().name("pessoaWriter").delimited()
                .names("pessoaId", "agencia", "conta", "banco", "id").build();
    }

}
