package com.springbatch.dbpartitionerlocaljob.batch.reader;

import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import com.springbatch.dbpartitionerlocaljob.util.GenericsUtils;

public abstract class ReaderConfig<T> {

  private final DataSource dataSource;
  private final Integer pageSize;
  private final String name;
  private final PagingQueryProvider pagingQueryProvider;
  private final String selectClause;
  private final String fromClause;
  private final String sortKey;
  private final String whereClause;
  private final Class<T> entityClass;

  @SuppressWarnings("squid:S107")
  protected ReaderConfig(final DataSource dataSource, final Integer pageSize, final String name,
      final PagingQueryProvider pagingQueryProvider,
      final String selectClause, final String fromClause, final String sortKey, final String whereClause) {
    this.dataSource = dataSource;
    this.pageSize = Objects.isNull(pageSize) ? 2000 : pageSize;
    this.name = name;
    this.pagingQueryProvider = pagingQueryProvider;
    this.selectClause = selectClause;
    this.fromClause = fromClause;
    this.sortKey = sortKey;
    this.whereClause = whereClause;
    this.entityClass = GenericsUtils.getInstance().getGenericsInfo(this).getType(0);
  }

  public JdbcPagingItemReader<T> reader() {
    return new JdbcPagingItemReaderBuilder<T>()
        .name(String.format("%sReader", this.name))
        .dataSource(this.dataSource)
        .queryProvider(this.pagingQueryProvider)
        .pageSize(pageSize)
        .rowMapper(new BeanPropertyRowMapper<>(this.entityClass))
        .build();
  }

  public SqlPagingQueryProviderFactoryBean queryProvider(final Long minValue, final Long maxValue) {
    SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
    factoryBean.setSelectClause(selectClause);
    factoryBean.setFromClause(fromClause);
    factoryBean.setWhereClause(String.format(whereClause, minValue, maxValue));
    factoryBean.setSortKey(sortKey);
    factoryBean.setDataSource(this.dataSource);
    return factoryBean;
  }

  public DataSource getDataSource() {
    return this.dataSource;
  }

  public Integer getPageSize() {
    return this.pageSize;
  }

  public String getName() {
    return this.name;
  }

  public PagingQueryProvider getPagingQueryProvider() {
    return this.pagingQueryProvider;
  }

  public String getSelectClause() {
    return this.selectClause;
  }

  public String getFromClause() {
    return this.fromClause;
  }

  public String getSortKey() {
    return this.sortKey;
  }

  public String getWhereClause() {
    return this.whereClause;
  }

}
