package com.springbatch.bdremotepartitioner.batch.writer;

import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springbatch.bdremotepartitioner.core.dominio.DadosBancarios;

@Configuration
public class BancoDadosBancariosWriterConfig {

    private final DataSource dataSource;

    public BancoDadosBancariosWriterConfig(
            @Qualifier("appDataSource") final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcBatchItemWriter<DadosBancarios> dadosBancariosWriter() {
        return new JdbcBatchItemWriterBuilder<DadosBancarios>().dataSource(dataSource).sql(
                "INSERT INTO dados_bancarios (id, pessoa_id, agencia, conta, banco) VALUES (:id, :pessoaId, :agencia, :conta, :banco)")
                .beanMapped().build();
    }
}
