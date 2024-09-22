package com.springbatch.dbpartitionerlocaljob.writer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.dbpartitionerlocaljob.dominio.Pessoa;

@Configuration
public class BancoPessoaWriterConfig {
  @Bean
  public JdbcBatchItemWriter<Pessoa> bancoPessoaWriter(@Qualifier("appDataSource") DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Pessoa>()
        .dataSource(dataSource)
        .sql(
        "INSERT INTO pessoa (id, nome, email, data_nascimento, idade) VALUES (:id, :nome, :email, :dataNascimento, :idade)")
        .beanMapped()
        .build();
  }
}
