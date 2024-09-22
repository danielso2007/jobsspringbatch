package com.springbatch.dbpartitionerlocaljob.batch.reader;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import com.springbatch.dbpartitionerlocaljob.code.dominio.Pessoa;

@Configuration
public class BancoPessoaReaderConfig {

  @StepScope
  @Bean
  public JdbcPagingItemReader<Pessoa> pessoaReader(@Qualifier("appDataSource") DataSource dataSource,
      @Qualifier("queryProviderPessoa") PagingQueryProvider pagingQueryProvider) {
    return new JdbcPagingItemReaderBuilder<Pessoa>()
        .name("pessoaReader")
        .dataSource(dataSource)
        .queryProvider(pagingQueryProvider)
        .pageSize(2000)
        .rowMapper(new BeanPropertyRowMapper<>(Pessoa.class))
        .build();
  }

  @StepScope
  @Bean
  public SqlPagingQueryProviderFactoryBean queryProviderPessoa(@Qualifier("appDataSource") DataSource dataSource,
      @Value("#{stepExecutionContext['minValue']}") Long minValue,
      @Value("#{stepExecutionContext['maxValue']}") Long maxValue) {
    SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
    factoryBean.setSelectClause("*");
    factoryBean.setFromClause("FROM pessoa_origem");
    factoryBean.setWhereClause(String.format("WHERE id >= %d AND id <= %d", minValue, maxValue));
    factoryBean.setSortKey("id");
    factoryBean.setDataSource(dataSource);
    return factoryBean;
  }

}
