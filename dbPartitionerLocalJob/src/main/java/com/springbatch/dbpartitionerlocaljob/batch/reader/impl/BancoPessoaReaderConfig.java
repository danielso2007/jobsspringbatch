package com.springbatch.dbpartitionerlocaljob.batch.reader.impl;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springbatch.dbpartitionerlocaljob.batch.reader.ReaderConfig;
import com.springbatch.dbpartitionerlocaljob.code.dominio.Pessoa;

@Configuration
public class BancoPessoaReaderConfig extends ReaderConfig<Pessoa> {

  protected BancoPessoaReaderConfig(@Qualifier("appDataSource") final DataSource dataSource,
      @Qualifier("queryProviderPessoa") final PagingQueryProvider pagingQueryProvider) {
    super(dataSource, 2000, "pessoa", pagingQueryProvider, "*", "FROM pessoa_origem", "id",
        "WHERE id >= %d AND id <= %d");
  }

  @StepScope
  @Bean
  public JdbcPagingItemReader<Pessoa> pessoaReader() {
    return reader();
  }

  @StepScope
  @Bean
  public SqlPagingQueryProviderFactoryBean queryProviderPessoa(
      @Value("#{stepExecutionContext['minValue']}") final Long minValue,
      @Value("#{stepExecutionContext['maxValue']}") final Long maxValue) {
    return queryProvider(minValue, maxValue);
  }

}
