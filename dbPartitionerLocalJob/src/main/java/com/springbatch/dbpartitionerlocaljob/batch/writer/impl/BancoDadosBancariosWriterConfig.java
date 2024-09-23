package com.springbatch.dbpartitionerlocaljob.batch.writer.impl;

import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springbatch.dbpartitionerlocaljob.batch.writer.WriterConfig;
import com.springbatch.dbpartitionerlocaljob.code.dominio.DadosBancarios;

@Configuration
public class BancoDadosBancariosWriterConfig implements WriterConfig<DadosBancarios> {
    @Bean
    public JdbcBatchItemWriter<DadosBancarios> dadosBancariosWriter(
            @Qualifier("appDataSource") DataSource dataSource) {
        return writer(dataSource,
                "INSERT INTO dados_bancarios (id, pessoa_id, agencia, conta, banco) VALUES (:id, :pessoaId, :agencia, :conta, :banco)");
    }
}
