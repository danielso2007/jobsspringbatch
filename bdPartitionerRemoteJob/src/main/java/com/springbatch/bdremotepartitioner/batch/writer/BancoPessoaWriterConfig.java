package com.springbatch.bdremotepartitioner.batch.writer;

import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springbatch.bdremotepartitioner.core.dominio.Pessoa;

@Configuration
public class BancoPessoaWriterConfig {

    private final DataSource dataSource;

    public BancoPessoaWriterConfig(@Qualifier("appDataSource") final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcBatchItemWriter<Pessoa> pessoaWriter() {
        return new JdbcBatchItemWriterBuilder<Pessoa>().dataSource(dataSource).sql(
                "INSERT INTO pessoa (id, nome, email, data_nascimento, idade) VALUES (:id, :nome, :email, :dataNascimento, :idade)")
                .beanMapped().build();
    }
}
