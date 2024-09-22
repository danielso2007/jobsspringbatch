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
import com.springbatch.dbpartitionerlocaljob.code.dominio.DadosBancarios;

@Configuration
public class BancoDadosBancariosReaderConfig {

  @StepScope
  @Bean
  public JdbcPagingItemReader<DadosBancarios> dadosBancariosReader(@Qualifier("appDataSource") DataSource dataSource,
      @Qualifier("queryProviderDadosBancarios") PagingQueryProvider pagingQueryProvider) {
    return new JdbcPagingItemReaderBuilder<DadosBancarios>()
        .name("dadosBancariosReader")
        .dataSource(dataSource)
        .queryProvider(pagingQueryProvider)
        .pageSize(2000)
        .rowMapper(new BeanPropertyRowMapper<>(DadosBancarios.class))
        .build();
  }

  @StepScope
  @Bean
  public SqlPagingQueryProviderFactoryBean queryProviderDadosBancarios(
      @Qualifier("appDataSource") DataSource dataSource,
      @Value("#{stepExecutionContext['minValue']}") Long minValue,
      @Value("#{stepExecutionContext['maxValue']}") Long maxValue) {
    SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
    factoryBean.setSelectClause("*");
    factoryBean.setFromClause("FROM dados_bancarios_origem");
    factoryBean.setWhereClause(String.format("WHERE id >= %d AND id <= %d", minValue, maxValue));
    factoryBean.setSortKey("id");
    factoryBean.setDataSource(dataSource);
    return factoryBean;
  }

}
