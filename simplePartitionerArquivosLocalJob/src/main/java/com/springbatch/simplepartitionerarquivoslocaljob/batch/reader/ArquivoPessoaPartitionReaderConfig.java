package com.springbatch.simplepartitionerarquivoslocaljob.batch.reader;

import java.util.Date;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.validation.BindException;
import com.springbatch.simplepartitionerarquivoslocaljob.code.dominio.Pessoa;

@Configuration
public class ArquivoPessoaPartitionReaderConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<Pessoa> arquivoPessoaPartitionReader(
            @Value("#{stepExecutionContext['file']}") Resource resource) {
        return new FlatFileItemReaderBuilder<Pessoa>()
                .name("arquivoPessoaReader")
                .resource(resource)
                .delimited()
                .names("nome", "email", "dataNascimento", "idade", "id")
                .addComment("--")
                .fieldSetMapper(fieldSetMapper())
                .build();
    }

    private FieldSetMapper<Pessoa> fieldSetMapper() {
        return new FieldSetMapper<Pessoa>() {
            @SuppressWarnings("null")
            @Override
            public Pessoa mapFieldSet(final FieldSet fieldSet) throws BindException {
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(fieldSet.readString("nome"));
                pessoa.setEmail(fieldSet.readString("email"));
                pessoa.setDataNascimento(
                        new Date(fieldSet.readDate("dataNascimento", "yyyy-MM-dd HH:mm:ss").getTime()));
                pessoa.setIdade(fieldSet.readInt("idade"));
                pessoa.setId(fieldSet.readInt("id"));
                return pessoa;
            }
        };
    }

}
