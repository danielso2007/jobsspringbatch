package com.springbatch.dbpartitionerlocaljob.batch.writer.impl;

import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springbatch.dbpartitionerlocaljob.batch.writer.WriterConfig;
import com.springbatch.dbpartitionerlocaljob.code.dominio.Pessoa;

@Configuration
public class BancoPessoaWriterConfig implements WriterConfig<Pessoa> {
  @Bean
  public JdbcBatchItemWriter<Pessoa> pessoaWriter(@Qualifier("appDataSource") DataSource dataSource) {
    return writer(dataSource,
        "INSERT INTO pessoa (id, nome, email, data_nascimento, idade) VALUES (:id, :nome, :email, :dataNascimento, :idade)");
  }
}
